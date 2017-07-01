#### Points vs. vectors
* Point: (2, 1) => x =2, y=1
* Vector represents *change*, e.g.:
[1,
 2]
  - Note different formatting: horizontal parens vs. vertical braces
  - Vectors not bound to any one point, are equal if the change they represent is equal
  - Represented with "overbar": v̅ (where bar is over text)

#### Operating on vectors
* Addition: if two vectors were placed end to end, where would they end up?
  Can simply add each individual coordinate
* Subtraction: v - w corresponds to the the vector from end of w to end of v, NOT from v to w. Corresponds to subtracting across each individual coordinate
* Scalar multiplication: "scales" a vector's length. Multiplies by each indiv. coordinate

#### Magnitude
* Length of a vector
* Via Pythag theorem, corresponds to sqrt x**2 + y**2. In n dimensions, model still applies: ||v|| (double bars: magnitude) = sqrt(x**2 + y**2 + ...)

#### Direction
* Unrelated to scale, so computed using "unit vector" of magnitude 1.
* Processing of finding unit vector: "normalizing". unit vector of v: "normalization" of v.
* (oh, and if all coords 0, it's the "0 vector", which indicates no change, has no direction)
* Normalization of vector b expressed as u (with vector arrow) subscript b

#### Dot product
* Multiply two vectors * each other (each item * its counterpart), then sum the list. Represented by a dot.

#### Inner product (angle between vectors)
* Angle between vectors, represented by theta (Θ)
* Note that any two vectors have *two* angles, the "long way" and the "short way" -- we always use the short way
* Represented by arc cosine of (dot product of two vectors) / (magnitudes of both vectors, multiplied)
* Note that two vectors form a triangle (if dots connected) or parallelogram -- useful in some applications (?)

#### Parallel & orthogonal vectors
* Vectors *parallel* if one is a scalar multiple of the other (or dot product is 1 or -1)
* Vectors *orthogonal* if their dot product is 0
* Zero vector parallel and orthogonal to all vectors, including itself!

#### Vector projections
* Imagine we have vector b, emanating from 0
* Line l passes through it, extending in both directions
* Now let's add another arbitrary vector, v, also emanating from 0
* To "project" v onto b, we imagine rotating the angle at which we view both so that v and b are aligned. v will appear shorter; this shortened version is the "projection" of v onto b. That's proj(sub b)(v), or v" (two bars raised, a la superscript)
* OK, we can express v as the sum of two other vectors: v_parallel (the projection vector), and v_perp (the one that's orthogonal to b). ||v_parallel|| = dot product (v, normalization of b). Then, v_parallel itself (as opposed to just its length) can be computed by multiplying that length * the normalization of b.
* So... v" = (v.dot_product(b.normalization())) * b.normalization()

#### Cross products
* Another form of vector multiplication, only exists in exactly three dimensions. (Can be sort of handled in 2 by setting z to 0, though.)
* Given vectors v and w, cross product is the vector that's *orthogonal to both v and w*, and has magnitude of ||v|| * ||w|| * sin(Θ), where Θ is the angle between v and w
* If either vector is the 0 vector, or if vectors are parallel, cross product is 0 vector
* In principle, two vectors produced; we use the "right-hand rule" to decide which to use. This depends on the order of multiplication. So, switching order of multiplication negates the product! anticommutative
* How to do it: given v [x1,y1,z1] and w [x2,y2,z2], cross product (expressed with x: v x w) is [(y1z2 - y2z1), -(x1z2 - x2z1), (x1y2 - x2y1)]
