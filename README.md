# Projekan
Projekan is a simple project management app. You can manage your projects such as application, website, game, or UI/UX design. Then, you can track your project progress based on how many tasks that you have done. Also, this app has simple design and very easy to use, so that you can manage your projects easily.

## Architecture
This app implements Clean Architecture which has three main layers, UI/Presentation, Domain, and Data layer.

**References**:
- [The Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Clean Architecture Guide (with tested examples): Data Flow != Dependency Rule](https://medium.com/proandroiddev/clean-architecture-data-flow-dependency-rule-615ffdd79e29)

## Tech Stack and Libraries
- Kotlin
- Jetpack Compose
- Dagger Hilt
- Paging 3
- Flow
- Coroutines
- Jetpack DataStore
- Google Accompanist
- Retrofit
- Moshi
- Coil
- Mockito

## Features
1. Read all projects
2. Read all deadlines
3. Add, edit, and delete a project
4. Add, edit, and delete a task
5. Project detail which also can track your project progress based on how many tasks that you have done.

## Preview
**Home**
![Home](https://res.cloudinary.com/dysojzcqm/image/upload/w_360,h_800/v1670824433/projekan_screenshot/01_atx4wm.jpg)

**Add Project**
![Add Project](https://res.cloudinary.com/dysojzcqm/image/upload/w_360,h_800/v1670824434/projekan_screenshot/02_ppj6ss.jpg)

**Project Detail**
![Project Detail](https://res.cloudinary.com/dysojzcqm/image/upload/w_360,h_800/v1670824434/projekan_screenshot/03_x6blbu.jpg)

**Add Task**
![Add Task](https://res.cloudinary.com/dysojzcqm/image/upload/w_360,h_800/v1670824434/projekan_screenshot/04_r3waxo.jpg)

**Deadlines**
![Deadlines](https://res.cloudinary.com/dysojzcqm/image/upload/w_360,h_800/v1670824433/projekan_screenshot/05_kjmh9q.jpg)

## Installation and Usage
Download the zip from this repository or use git clone on your terminal.

```bash
git clone https://github.com/ajailani4/projekan-android.git
```
Then, run it on your Android emulator or physical device.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.