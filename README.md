# Mines-Stake-DSA

A project combining the classic gameplay of Minesweeper with “Mines” on Stake for a DSA project.

# 🎯 Overview

Mines-Stake-DSA merges elements of Minesweeper — uncovering safe tiles, avoiding “mines” — with a casino-inspired betting system. It’s designed as a DSA project to practice algorithms, game logic, and possibly risk/reward mechanics.

# 📂 Project Structure
/src               — Java source code  
/out/production    — Compiled output / build artifacts  
.idea              — IDE configuration (if using IntelliJ IDEA)  
.gitattributes     — Git attributes  # Mines-Stake-DSA

A project combining the classic gameplay of Minesweeper with “Mines” on Stake for a DSA project

# 🎯 Overview

Mines-Stake-DSA merges elements of Minesweeper — uncovering safe tiles, avoiding “mines”  with a casino-inspired betting system. It’s designed as a DSA project to practice algorithms, game logic, and possibly risk/reward mechanics


# 🚀 Getting Started
## Requirements
```bash
# Check Java version (must be 21+)
java -version

# Expected output:
# java version "21.0.x"
```
(Optional) An IDE like IntelliJ IDEA or Eclipse

## How to Run

Clone the repository
```bash
git clone https://github.com/oct1st68/Mines-Stake-DSA.git
cd Mines-Stake-DSA
```


Build and run using your IDE, or via command line:
```bash
# Method 1: From IDE
Right-click Main.java > Run 'Main.main()'

# Method 2: From terminal
javac -d out -sourcepath src src/Main/Main.java
java -cp out:res Main.Main
```

# 🧠 How It Works

- Players choose a grid (similar to Minesweeper).

- A number of bombs are randomly placed on the board.

- Players reveal tiles one by one:
If a tile is safe → the player continues.

If a mine is uncovered → game over (or lose stake), similar to casino risk.

(Optional) A betting or stake system: players place a “bet” before unveiling tiles; winning depends on how long you avoid mines.

🚀 Getting Started
Prerequisites

Java 8 or higher

(Optional) An IDE like IntelliJ IDEA or Eclipse

How to Run

Clone the repository

git clone https://github.com/oct1st68/Mines-Stake-DSA.git


Build and run using your IDE, or via command line:

javac -d out/production src/**/*.java  
java -cp out/production <MainClassName>  


Follow on-screen instructions to play the game.

🧠 How It Works

Players choose a grid (similar to Minesweeper).

A number of “mines” are randomly placed on the board.

Players reveal tiles one by one:

If a tile is safe → the player continues.

If a mine is uncovered → game over (or lose stake), similar to casino risk.

(Optional) A betting or stake system: players place a “bet” before unveiling tiles; winning depends on how long you avoid mines.
