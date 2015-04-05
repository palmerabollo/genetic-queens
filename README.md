# genetic-queens

N-queens problem solved using genetic algorithms in Java (with watchmaker), as exposed in
["Algoritmos Genéticos"](http://slides.com/guidogarcia/algoritmos-geneticos).

The main entry point is under [src/main/java/net/guidogarcia/queens/Queens.java](https://github.com/palmerabollo/genetic-queens/blob/master/src/main/java/net/guidogarcia/queens/Queens.java)

## Output Example

```
> java Queens

Generation 0: [3, 0, 6, 4, 2, 5, 3, 4] => 3.00, avg 8.05
Generation 1: [2, 0, 5, 1, 4, 6, 7, 3] => 1.00, avg 7.34
Generation 2: [2, 0, 5, 1, 4, 6, 7, 3] => 1.00, avg 7.16
Generation 3: [2, 0, 5, 1, 4, 6, 7, 3] => 1.00, avg 6.75
Generation 4: [6, 4, 2, 0, 5, 7, 1, 3] => 0.00, avg 6.53
· · · · · * · · 
* · · · · · · · 
· · · · * · · · 
· * · · · · · · 
· · · · · · · * 
· · * · · · · · 
· · · · · · * · 
· · · * · · · · 


```

The best individual is shown after each generation. Every individual is represented as an array of integers. These integers represent the queen's position in that column.

Current implementation uses the number of mutual attacks as fitness value. The algorithm stops when no mutual attacks are found.

## License

Released under the "DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE"

