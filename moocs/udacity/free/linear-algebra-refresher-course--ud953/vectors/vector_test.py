import unittest
from vector import Vector


class TestVectorMethods(unittest.TestCase):

    def test_addition(self):  # methods must start with `test` to run
        v = Vector([1, 2])
        w = Vector([3, 4])
        self.assertEqual(v + w, Vector([4, 6]))

    def test_subtraction(self):
        v = Vector([1, 2])
        w = Vector([3, 4])
        self.assertEqual(v - w, Vector([-2, -2]))

    def test_scalar_multiplication(self):
        v = Vector([1, 2])
        scale = 10
        self.assertEqual(v.get_scalar_multiply(10), Vector([10, 20]))

    def test_magnitude(self):
        v = Vector([3, 4])
        self.assertEqual(v.get_magnitude(), 5)

    def test_direction(self):
        v = Vector([1, 2])
        unit_vector = v.get_direction()
        self.assertEqual(unit_vector.get_coords_rounded(3), [0.447, 0.894])

    def test_dot_product(self):
        v = Vector([1, 2])
        w = Vector([3, 4])
        self.assertEqual(v.get_dot_product(w), 11)

    def test_inner_product(self):
        v = Vector([0, 1])
        w = Vector([1, 0])
        inner_product = v.get_inner_product(w)
        self.assertEqual(inner_product["degrees"], 90)
        self.assertEqual(round(inner_product["radians"], 3), 1.571)

    def test_parallelogram_area(self):
        v = Vector([-8.987, -9.838, 5.031])
        w = Vector([-4.268, -1.861, -8.866])
        self.assertEqual(round(v.get_parallelogram_area(w), 3), 142.122)

    def test_triangle_area(self):
        v = Vector([1.5, 9.547, 3.691])
        w = Vector([-6.007, 0.124, 5.772])
        self.assertEqual(round(v.get_triangle_area(w), 3), 42.565)

    def test_parallel(self):
        v = Vector([1, 2])
        w = Vector([-2, -4])
        x = Vector([1, 3])
        self.assertTrue(v.is_parallel_with(w))
        self.assertFalse(v.is_parallel_with(x))

    def test_projection(self):
        v = Vector([-9.88, -3.264, -8.159])
        w = Vector([-2.155, -9.353, -9.473])
        coords_rounded = v.get_projection(w).get_coords_rounded(3)
        self.assertEqual(coords_rounded, [-1.530, -6.640, -6.725])

    def test_orthogonal(self):
        v = Vector([1, 2])
        w = Vector([-2, 1])
        x = Vector([1, 3])
        self.assertTrue(v.is_orthogonal_to(w))
        self.assertFalse(v.is_orthogonal_to(x))

    def test_orthogonal_component(self):
        v = Vector([-9.88, -3.264, -8.159])
        w = Vector([-2.155, -9.353, -9.473])
        coords_rounded = v.get_orthogonal_component(w).get_coords_rounded(3)
        self.assertEqual(coords_rounded, [-8.350, 3.376, -1.434])

    def test_cross_product(self):
        v = Vector([5, 3, -2])
        w = Vector([-1, 0, 3])
        cross_product = v.get_cross_product(w)
        self.assertEqual(cross_product, Vector([9, -13, 3]))
        self.assertTrue(cross_product.is_orthogonal_to(v))
        self.assertTrue(cross_product.is_orthogonal_to(w))
