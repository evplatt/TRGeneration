Team Members:  Seung Hun (Stan) Lee, Evan Platt

Project Title:  Control Flow Graph and Structural Test Requirement Generator

Description of the Proposed Work

Our proposal is to create a program, written in Java, that takes as inputs (1) the source code of a function and (2) a desired coverage criteria, and outputs (1) a control flow graph and (1) a set of test requirements satisfying the specified coverage criteria.

The first module of the program, the graph generator, is responsible for parsing the input function’s source code and generating a control flow graph made up of nodes and edges.  The graph generator will support syntax-correct input functions written in Java and will be able to parse a limited set of control flow statements including if-then and if-then-else conditionals and for, while, and do-while loops.  In parsing the input function’s source code, the graph generator will populate a linked list of node objects.  Each node object will contain the node’s number and a list of next nodes.  The test requirement generator will use this linked list as its input.  Lastly, the graph generator will output a pictorial representation of the function’s graph.

The second module of the program, the test requirement generator, takes charge of reading the linked list of node objects generated from the graph generator and listing test requirements for a desired coverage criteria. After a user choose one of the coverage criteria including node coverage, edge coverage, edge-pair coverage, and prime path coverage, the test requirement generator will print out test requirements according to the user-chosen one. This generator might be expanded to include test paths if time for implementation allows.
