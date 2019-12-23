# connect4

## Build and run

```bash
mvn package

cd target

java -jar connect4-0.0.1-SNAPSHOT.jar

```

## Design Ideas

Have a class Cell. The board contains a collection of cells.
The cells contain the position and state.

i.e.
  Cell
    column 1
    row    2
    state  O,X,EMPTY

Then filter the collection to find all cells where empty cells then filter to find the column numbers.

```
|1|2|3|4|5|6|7|
+-+-+-+-+-+-+-+
| | | | |O| | |
| | | | |X| | |
| | |X|X|X| | |
| | |O|X|O| | |
| | |X|O|X|O| |
| | |O|X|O|X|O|

  |1|2|3|4|5|6|7|
  +-+-+-+-+-+-+-+
1 | | | | | | | |
2 | | | | |X| | |
3 | | |X|X|X| | |
4 | | | |X| | | |
5 | | |X| |X| | |
6 | | | |X| |X| |

            (2,5)
(3,3),(3,4),(3,5)
      (4,4)
(5,3),     ,(5,5)
      (6,4),     ,(6,6)
```