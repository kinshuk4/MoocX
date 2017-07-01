import math
from decimal import Decimal, getcontext

getcontext().prec = 7  # After which we consider two numbers equal


class Vector(object):

    def __init__(self, coords):
        self.coords = tuple([Decimal(x) for x in coords])

    def __str__(self):
        return "Coordinates: {}".format(self.coords)

    def __eq__(self, other):
        return self.coords == other.coords

    def __add__(self, other):
        """ Implicitly called in `+` operations. """
        return Vector(self._merge_with_transformation(
            other,
            lambda x, y: x + y)
        )

    def __sub__(self, other):
        """ Implicitly called in `-` operations. """
        return Vector(self._merge_with_transformation(
            other,
            lambda x, y: x - y)
        )

    def _merge_with_transformation(self, other, transform):
        """ Merges lists, applies `transform` to each item pair. """
        return [transform(x, y) for x, y in zip(self.coords, other.coords)]

    def get_coords_rounded(self, decimal_place):
        """ Returns `self.coords` rounded to requested decimal place. """
        return [round(x, decimal_place) for x in self.coords]

    def get_scalar_multiply(self, scale):
        """ Multiplies `scale` by each item in `self`. """
        return Vector([item * scale for item in self.coords])

    def get_magnitude(self):
        """ Returns `self`'s length (magnitude). """
        return Decimal(math.sqrt(sum([x**2 for x in self.coords])))

    def get_direction(self):
        """ Returns `self`'s direction / 'unit vector' / 'normalization'. """
        return self.get_scalar_multiply(1 / self.get_magnitude())

    def get_dot_product(self, other):
        """ Returns sum of each item pair multiplied. """
        items_multiplied = self._merge_with_transformation(
            other,
            lambda x, y: x * y
        )
        return sum(items_multiplied)

    def get_inner_product(self, other):
        """ Returns the angle between `self` and `other`. """
        dot_product = self.get_dot_product(other)
        magnitudes_multiplied = self.get_magnitude() * other.get_magnitude()
        inner_product_radians = math.acos(dot_product / magnitudes_multiplied)
        return {
            "radians": inner_product_radians,
            "degrees": math.degrees(inner_product_radians)
        }

    def get_parallelogram_area(self, other):
        """ Returns area of parallelogram formed by self and other. """
        return self.get_cross_product(other).get_magnitude()

    def get_triangle_area(self, other):
        """ Returns area of triangel formed by self and other. """
        return self.get_parallelogram_area(other) / 2

    def is_parallel_with(self, other):
        """ Returns boolean indicating if `self` is parallel with `other`. """
        direction_self = self.get_direction()
        direction_other = other.get_direction()
        dot_product = direction_self.get_dot_product(direction_other)
        return dot_product == Decimal(1) or dot_product == Decimal(-1)

    def get_projection(self, other):
        """ Returns component of `self` that's parallel to `other`. """
        other_direction = other.get_direction()
        scale = self.get_dot_product(other_direction)
        return other_direction.get_scalar_multiply(scale)

    def is_orthogonal_to(self, other):
        """ Returns boolean indicating if `self` is orthogonal to `other`. """
        return self.get_dot_product(other) == Decimal(0)

    def get_orthogonal_component(self, other):
        """ Returns component of `self` that's orthogonal to `other`. """
        projection = self.get_projection(other)
        return self - projection

    def get_cross_product(self, other):
        """ Returns cross product of self x other. Anticommutative! """
        x1, y1, z1 = self.coords
        x2, y2, z2 = other.coords
        return Vector([
            (y1 * z2 - y2 * z1),
            -(x1 * z2 - x2 * z1),
            (x1 * y2 - x2 * y1)
        ])
