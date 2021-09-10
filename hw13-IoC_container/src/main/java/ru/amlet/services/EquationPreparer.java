package ru.amlet.services;

import java.util.List;

import ru.amlet.model.Equation;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
