package com.challange.lumiparser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.challange.lumiparser.room.LayoutDao
import com.challange.lumiparser.room.LayoutDatabase
import com.challange.lumiparser.room.LayoutRepository
import com.challange.lumiparser.room.LayoutRepositoryImpl
import com.challange.lumiparser.room.models.Layout
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: LayoutDatabase
    private lateinit var dao: LayoutDao
    private lateinit var repository: LayoutRepository

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LayoutDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.dao
        repository = LayoutRepositoryImpl(dao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testUpsertingAndReceivingTheLayoutViaDao() = runTest {
        val layout = Layout(id = 1, layoutJson = "{\"example\":\"data\"}")
        dao.upsert(layout)

        val result = dao.getFirst().first()
        assertEquals(layout, result)
    }

    @Test
    fun testUpsertingAndReceivingTheLayoutViaRepository() = runTest {
        val layout = Layout(id = 1, layoutJson = "{\"example\":\"data\"}")
        repository.upsertLayout(layout)

        val result = repository.getFirstLayout().first()
        assertEquals(layout, result)
    }

    @Test
    fun testUpsertLayoutOverwritesExisting() = runTest {
        val layout1 = Layout(id = 1, layoutJson = "{\"example\":\"data1\"}")
        val layout2 = Layout(id = 1, layoutJson = "{\"example\":\"data2\"}")

        dao.upsert(layout1)
        dao.upsert(layout2)
        val result = dao.getFirst().first()

        assertEquals(layout2, result)
    }
}