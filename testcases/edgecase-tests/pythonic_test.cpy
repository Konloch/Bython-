import sys

def test_function(x):
    y = x * x;
    z = y + 5;
    print(z);

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("function_interop.bpp [number]")
        sys.exit(1)

    test_function(int(sys.argv[1]))