# Lumi Parser

**Lumi Parser** is an Android application designed to parse and render custom JSON-based UI layouts. Built with Jetpack Compose and modern Android architecture components.

---

## 🧰 Tech Stack

- Kotlin
- Jetpack Compose
- ViewModel (AndroidX)
- Koin (Dependency Injection)
- Retrofit
- Room
- Moshi
- Coil
- JUnit
- Mockito
- Coroutines & Flow

## 📱 Screenshots
<img width="200" alt="Screenshot_20251015-193324" src="https://github.com/user-attachments/assets/70f4db0e-70bb-4803-a1bd-4223cae503cf" />
<img width="200" alt="Screenshot_20251015-193139" src="https://github.com/user-attachments/assets/79925279-a6f9-4407-b750-b15e76f58c95" />
<img width="200" alt="Screenshot_20251015-193146" src="https://github.com/user-attachments/assets/ac35913a-fd03-491a-a5b5-49e0ab8dce0a" />


---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/lumi-parser.git
cd lumi-parser
```

### 2. Open the project in Android Studio
- Open Android Studio.
- Choose File > Open, then select the project folder.
- Let Gradle sync complete.

### 3. Set up local properties (if needed)
If you want to use a custom server base url, add the following to local.properties:

```bash
BASE_URL=https://your.api.url/
```

### 4. Run the app
- Select an emulator or physical device.
- Press Run ▶️ or use the shortcut Shift + F10

## 🧪 Testing

### Unit Tests

To run unit tests:

1. Open the `test/` directory in Android Studio.
2. Right-click a class or package > **Run 'Tests in...'**.
3. Or press `Ctrl + Shift + F10` while inside a test class.

### UI / Instrumentation Tests

1. Open the `androidTest/` directory.
2. Right-click a test class (e.g. in `ui/` or `screens/`) > **Run 'Tests in...'**.
3. Or select **Run > Run... > Choose Instrumented Test** from the top menu.

> You can also view results in the **Test Results** panel after running.

## 📁 Project Structure
```bash
├── di/             # Koin modules and providers
├── retrofit/       # Retrofit API
├── room/           # Room DB, repositories
├── ui/             # Composables, ViewModels, UI state
├── utils/          # Utility methods for the app 
├── viewmodel/      # viewmodels
```

## ✅ Requirements
- Android Studio Narwhal (or newer)
- Android SDK 36
- Kotlin 2.2.0
- Gradle 8.13
