# connect4

[![Build Status](https://dev.azure.com/chrisdbarran/connect4/_apis/build/status/chrisdbarran.connect4?branchName=master)](https://dev.azure.com/chrisdbarran/connect4/_build/latest?definitionId=4&branchName=master)

## Design Ideas

Have a class Cell. The board contains a collection of cells.
The cells contain the position and state.

i.e. 
  Cell
    column 1
    row    2
    state  O,X,EMPTY

Then filter the collection to find all cells where empty cells then filter to find the column numbers.
