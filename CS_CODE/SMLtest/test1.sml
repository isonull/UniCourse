datatype power = Cons of real * (unit -> power);

fun constant (c:real) = Cons(c, fn () => constant 0.0);

