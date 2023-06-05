from dataclasses import dataclass
from typing import List


class Type:
    pass

class Integer(Type):
    pass

class Boolean(Type):
    pass

class Expression:
    pass

class Operator(Expression):
    a: Expression
    b: Expression

    def eval(self):
        raise NotImplementedError()

@dataclass
class Constant(Expression):
    val: int | bool

    def eval(self, vars):
        return self.val

@dataclass
class Variable(Expression):
    name: str

    def eval(self, vars):
        return vars[self.name].eval(vars)

@dataclass
class Sum(Operator):
    a: Expression
    b: Expression

    def eval(self, vars):
        return self.a.eval(vars) + self.b.eval(vars)

@dataclass
class Product(Operator):
    a: Expression
    b: Expression

    def eval(self, vars):
        return self.a.eval(vars) * self.b.eval(vars)

@dataclass
class And(Operator):
    a: Expression
    b: Expression

    def eval(self, vars):
        return self.a.eval(vars) and self.b.eval(vars)
    
@dataclass
class Or(Operator):
    a: Expression
    b: Expression

    def eval(self, vars):
        return self.a.eval(vars) or self.b.eval(vars)

class Command:
    pass

@dataclass
class Declaration:
    var: str
    type: Type

@dataclass
class Assign(Command):
    var: Variable
    expr: Expression

    def eval(self, vars):
        vars[self.var.name] = self.expr
        return vars

@dataclass
class Print(Command):
    expr: Expression

    def eval(self, vars):
        print(self.expr.eval(vars))

class Program:
    def __init__(self, declarations: List[Declaration], commands: List[Command]):
        self.declarations = {dec.var: dec.type for dec in declarations}
        self.commands = commands
        if not self.are_types_correct():
            print("WRONG TYPES")

    def are_types_correct(self):
        vars = {}
        for cmd in self.commands:
            if type(cmd) == Assign:
                vars.update(cmd.eval(vars))
                if type(cmd.expr.eval(vars)) == int:
                    if type(self.declarations[cmd.var.name]) != Integer:
                        return False
                if type(cmd.expr.eval(vars)) == bool:
                    if type(self.declarations[cmd.var.name]) != Boolean:
                        return False
        return True


    def eval(self):
        vars = {}
        for cmd in self.commands:
            new_vars = cmd.eval(vars)
            if new_vars:
                vars.update(new_vars)

dec1 = Declaration("x", Integer())
dec2 = Declaration("y", Integer())
dec3 = Declaration("z", Integer())
dec4 = Declaration("a", Boolean())
dec5 = Declaration("b", Boolean())
dec6 = Declaration("c", Boolean())
dec7 = Declaration("test", Boolean())

command1 = Assign(Variable("x"), Constant(4))
command2 = Assign(Variable("y"), Sum(Constant(2), Constant(3)))
command3 = Assign(Variable("z"), Product(Variable("x"), Variable("y")))
command4 = Print(Variable("z"))
command5 = Print(Constant(4))
command6 = Assign(Variable("a"), Constant(False))
command7 = Assign(Variable("b"), And(Constant(True), Constant(True)))
command8 = Assign(Variable("c"), Or(Variable("a"), Variable("b")))
command9 = Print(Variable("c"))
command10 = Print(Constant(False))
command11 = Assign(Variable("test"), Or(Variable("b"), Variable("a")))

declarations = [dec1, dec2, dec3, dec4, dec5, dec6, dec7]
commands = [command1, command2, command3, command4, command5, command6, command7, command8, command9, command10, command11]
program = Program(declarations, commands)
program.eval()
