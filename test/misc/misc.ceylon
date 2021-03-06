shared interface X {
    shared void helloWorld() {
       print("hello world");
    }
}

shared class Foo(String name) {
    shared String name = name;
    variable value counter:=0;
    shared Integer count { return counter; }
    shared void inc() { counter:=counter+1; }
    shared default void printName() {
        print("foo name = " + name);
    }
    inc();
}

shared class Bar() extends Foo("Hello") satisfies X {
    shared actual void printName() {
        print("bar name = " + name);
        super.printName();
    }
    shared class Inner() {
        print("creating inner class of :" 
            + outer.name);
        shared void incOuter() {
            inc();
        }
    }
}

void printBoth(String x, String y) {
    print(x + ", " + y);
}

void doIt(void f()) {
    f(); f();
}

object foob {
    shared String name="Gavin";
}

void printAll(String... strings) {}

class F(String name) = Foo;
