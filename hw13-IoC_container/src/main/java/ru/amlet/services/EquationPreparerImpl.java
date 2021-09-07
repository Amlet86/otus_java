package ru.amlet.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.amlet.model.DivisionEquation;
import ru.amlet.model.Equation;
import ru.amlet.model.MultiplicationEquation;

public class EquationPreparerImpl implements EquationPreparer {
    @Override
    public List<Equation> prepareEquationsFor(int base) {
        List<Equation> equations = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            var multiplicationEquation = new MultiplicationEquation(base, i);
            var divisionEquation = new DivisionEquation(multiplicationEquation.getResult(), base);
            equations.add(multiplicationEquation);
            equations.add(divisionEquation);

        }
        Collections.shuffle(equations);
        return equations;
    }
}
