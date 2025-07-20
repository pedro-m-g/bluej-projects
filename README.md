# 🧪 BlueJ Projects ✨

This project is a collection of programming exercises based on the book
📘 [*Objects First with Java: A Practical Introduction Using BlueJ*](https://www.bluej.org/objects-first/).

I created these exercises for personal joy 🎈 — to rediscover and refresh programming concepts 🧠 in a safe, playful space 🎮.

---

## 📁 File Structure

| 📂 Directory  | 📝 Description                      |
| :------------ | :----------------------------------- |
| `01-shapes/`  | 🟦 Module for the "Shapes" exercise |
| `scripts/`    | 🛠 Dev scripts for build/run/test    |

---

## 🧰 Scripts

All scripts are runnable from anywhere — they automatically position themselves at the project root 🪄

| 🚀 Script                                 | 💡 What it does                             |
| :---------------------------------------- | :------------------------------------------ |
| `./scripts/full-build.sh`                 | 🧱 Compiles all the modules                 |
| `./scripts/full-test.sh`                  | 🧪 Runs all tests across modules            |
| `./scripts/build.sh <module>`             | 🔨 Compiles just the specified `<module>`   |
| `./scripts/test.sh <module>`              | ✔️  Runs tests for the specified `<module>` |
| `./scripts/start.sh <module> [<args>]`    | ▶️  Executes the given module with args     |

---

🌟 Example usage:

```bash
./scripts/build.sh 01-shapes
./scripts/start.sh 01-shapes demo square --verbose
./scripts/test.sh 01-shapes
```

Happy coding! ✨🐢🧑‍💻

## 📄 Legal Note

This repository does **not** contain any content from the book
*Objects First with Java* beyond the general inspiration for the exercises.
All source code and implementations are original and written from scratch.
This is a personal educational project and not affiliated with the book’s authors or publishers.

🪪 License: [CC0 1.0 Universal](https://creativecommons.org/publicdomain/zero/1.0/)
