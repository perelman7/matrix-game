## Matrix game (link on project: https://github.com/perelman7/matrix-game)

### This is game application where:
1. You send 2 parameters: url to configuration file (in file is located where you run the application, you send just filename), and bet amount
2. Game application must construct matrix with appointed params, check win combination, and count reward
3. As a result of execution of application we will see Input params, matrix, and Output result with 4 fields: matrix, reward, bonusFiled and win combinations

### Notes:
- in documentation 'bonus_symbols' described as list, but in json as one object and Note (2): Bonus symbol can be generated randomly in any cell(s) in the matrix I understand that like: 1) it may or may not be in matrix 2) the location is assigned randomly
- The reward count by next conditions:
  1) count for every symbol there multiplied reward ("A" : bet * combination_1 * combination_2 ...)
  2) then "B" : bet * combination_1 * combination_2 ...
  3) Then sum each symbol reward (a_reward + b_reward + ...)
  4) And then execute bonus reward (multiply or sum) a_b_..._reward +/* bonus_reward

### Technologies used
- java 17
- Serialization/Deserialization library - jackson (version: 2.15.2)
- annotation postprocessor - lomboc (version: 1.18.30)
- build tool - gradle (version 8.2)
- OS - Windows 10

### Building and Running process
- .\gradlew clean jar 
- java -jar .\build\libs\matrix-game-1.0-SNAPSHOT.jar --config config.json --betting-amount 100
- for testing was used provided configuration file (config.json)

#### Example execution
```json
INPUT:                                      
{--betting-amount=100, --config=config.json}

[E, A, D]
[C, F, E]
[5x, F, D]

OUTPUT:
{
  "matrix" : [ [ "E", "A", "D" ], [ "C", "F", "E" ], [ "5x", "F", "D" ] ],
  "reward" : 0,
  "applied_winning_combinations" : { },
  "applied_bonus_symbol" : null
}
```

