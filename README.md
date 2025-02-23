# 💡 IQ Puzzler Pro Solver
A simple interactive CLI and GUI program in Java language that implements the Brute Force algorithm to find solutions in the [**IQ Puzzler Pro**](https://www.smartgamesusa.com) game.

---

<!-- CONTRIBUTOR -->
<div align="center" id="contributor">
  <strong>
    <h3>~ Mandala GOAT! ~</h3>
    <table align="center">
      <tr align="center">
        <td>NIM</td>
        <td>Nama</td>
      </tr>
      <tr align="center">
        <td>13523004</td>
        <td>Razi Rachman Widyadhana</td>
      </tr>
    </table>
  </strong>
</div>

<div align="center">
  <h3 align="center">~ Tech Stacks ~ </h3>

  <p align="center">
    
[![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)][Java-url]
[![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)][Gradle-url]
[![JavaFX](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white)][JavaFX-url]
  
  </p>
</div>

---

## 🔎 Preview

  ![Demo](https://pouch.jumpshare.com/preview/sU-KQvyG1BGCk8Xo0yYvAwqGH6Nao_R2pTKNIUktw-X7C-pY3Df4tG6mpei7BaV-TlOAI8MID9_1LaUSXG4bpwDsGNdVGsBVcBkFe3NOasw)

## ✨ Features

### This project contains:

1. **Main Program as Puzzle Solver with Bruteforce approach for Default Puzzle Type**
2. **`(Bonus)` Solver for Custom Puzzle Type**
3. **`(Bonus)` Image Preview of the Solutions**
4. **`(Bonus)` GUI with including Available Test Cases, Outputs, File & Result Preview**


### **Space for Improvement:** 

1. **Implements Pyramid Puzzle Type**
2. **Implements `Add .txt file` option inside the App**


### **Take a peek:** 

1. **The core logic is located at `~/src/app/src/main/java/src/`** 
2. **There is an additional `GUI` folder for the GUI program**
3. **Create Pull Request and Collaborate for project improvement**

## 📦 Installation & Run

### Requirements
- [**Java**](Java-url) 21 or later
- [**Gradle**](Gradle-url) 8.12 or later *(if using build)*
- [**JavaFX**](JavaFX-url) 23 *(if using build)*

### Running the Application

The application can run in two modes: via **Command-Line Interface (CLI)** or **Graphical User Interface (GUI)**.

#### **1. Command-Line Interface (CLI) Mode**
- **Clone the repository**
- Run the following command to start the application in `CLI` mode:
   
   ```bash
   cd bin/src
   java -jar Algeo01-23002.jar -cli
   ```

- Alternatively, if you want to run it using `Gradle`:
   ```bash
   cd src
   ./gradlew run --quiet --warning-mode=none --console=plain --args="-cli"
   ```

#### **2. Graphical User Interface (GUI) Mode**
- **Clone the repository**
- Run the following command to start the application in `GUI` mode:
   
   ```bash
   cd bin/src
   java -jar Algeo01-23002.jar
   ```

- Alternatively, if you want to run it using `Gradle`:
   ```bash
   cd src
   ./gradlew run
   ```

---

## 🔧 Project Structure

```
├── bin          
│   └── src
│        └── app.jar    # the program .jar file
├── doc          
│   └── ...             # contains documents
│                       
├── src                 # contains Java source codes
│   └── app    
│        ├── ...
│        │    └── ...
│        │
│        └── src
│            ├── main
│            │    └── java
│            │          └── src
│            │               ├──  GUI
│            │               │     ├── GUI.java
│            │               │     └── OutputGUI.java
│            │               │
│            │               ├── Board.java
│            │               ├── Bruteforce.java
│            │               ├── CLI.java
│            │               ├── Input.java
│            │               ├── Main.java
│            │               ├── Output.java
│            │               ├── Piece.java
│            │               └── PuzzleImage.java
│            │
│            └── build.gradle.kts   # build setups
│                       
├── test                # test cases
│    └── ...
│
└── README.md           # brief explanation of the program
```

<!-- MARKDOWN LINKS & IMAGES -->
[Java-url]: https://www.java.com/en/
[Gradle-url]: https://gradle.org/
[JavaFX-url]: https://openjfx.io/
