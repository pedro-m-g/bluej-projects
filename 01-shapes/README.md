# 🟦 01-Shapes

This module contains the "Shapes" exercise from the book
📘 [*Objects First with Java: A Practical Introduction Using BlueJ*](https://www.bluej.org/objects-first/).

The goal is to create a simple application that can draw shapes (circles, squares, and triangles) based on user commands.

---

## 📁 File Structure

| 📂 Directory / 📝 File        | 📝 Description                                      |
| :---------------------------- | :-------------------------------------------------- |
| `src/main/`                   | ☕ Source code for the Shapes application           |
| `src/main/java/com/pedromg/bluej/shapes/` | 📍 Root package for all the app's classes |
| `src/main/java/com/pedromg/bluej/shapes/command/` | 💡 Defines the command-pattern classes |
| `src/main/java/com/pedromg/bluej/shapes/domain/` | 🎨 Contains the shape classes (Circle, Square, etc.) |
| `src/main/java/com/pedromg/bluej/shapes/ui/` | 🖼️ GUI components for displaying shapes |
| `src/test/`                   | 🧪 Unit tests for the Shapes module                 |
| `pom.xml`                     | 📦 Maven project configuration for this module      |

---

## 🚀 How to Run

All scripts are runnable from the project root.

### Build the Module

```bash
./scripts/build.sh 01-shapes
```

### Run Demos

To see a demonstration of the available shapes:

```bash
# Run a specific shape demo
./scripts/start.sh 01-shapes demo square
./scripts/start.sh 01-shapes demo circle
./scripts/start.sh 01-shapes demo triangle
```

### Run Tests

To run the unit tests for this module:

```bash
./scripts/test.sh 01-shapes
```

---

Happy coding! ✨🐢🧑‍💻
