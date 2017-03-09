# Simple-Slide_Puzzle-AI
Simple A* algorithm to solve sliding puzzles.

<p>Uses a constant called RESET_RATE to reset the priority queue before it becomes too large.</p>
<p>The RESET_RATE directly impacts the number of steps and inversely, the speed in which the algorithm solves the puzzle. As the RESET_RATE is reduced, it will solve in more steps, but solve the problem in faster time. 3x3's the RESET_RATE can be kept very high, but 4x4's the RESET_RATE should be no higher than 20, in 5x5's the RESET_RATE should be smaller than 10 if you want to finish without overloading the system (and waiting for 10 minutes).</p>
<p>The SAVED_FRONTIER determines how many states from the top of the PriorityQueue are saved across the reset. A value of about 25 worked well for 5x5 problems.</p>
<p>After solving prints out the steps to the solution.</p>
<p>All sliding puzzles should be equal in height and width.</p>
