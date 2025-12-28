package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;

public class GeneratePresetImpl implements GeneratePreset {

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {
        // Ваше решение который отвечает за генерацию пресета армии противника.
        // Цель данного метода — создать армию компьютера.
        // Метод формирует пресет армии компьютера, то есть максимально эффективный по
        // соотношению атаки к стоимости в первую очередь и соотношению здоровья к стоимости
        // набора юнитов разного типа таким образом, чтобы при этом соблюдалось ограничение
        // в 11 юнитов каждого типа.

        // В нём:
        // Параметр unitList — список юнитов, содержит объект юнита каждого типа. На его основе происходит заполнение армии компьютера. В данный момент существует четыре типа юнитов — лучник, всадник, мечник и копейщик.
        // Параметр maxPoints — максимальное число очков в сумме для всех юнитов армии, в данный момент — 1500.
        // Возвращаемое значение — возвращает объект армии компьютера со списком юнитов внутри неё.

        List<Unit> armyList = new ArrayList<>();
        List<String> limitedUnitTypeList = List.of();

        float atckToCoast = 0, healToCoast = 0;
        int index = 0, allowedUnits = 11;
        // находим самую низкую цену юнита
        int pointsLeft = maxPoints;
        int minCoast = unitList.get(0).getCost();
        for (Unit unit : unitList) {
            if (minCoast > unit.getCost())
                minCoast = unit.getCost();
        }

        while (pointsLeft > minCoast) {

            //проверяем сколько юнитов в листе и каковы их параметры
            // 1 условие выбора
            if (allowedUnits == 0)
                allowedUnits = 11;
            for (int i = 0; i < unitList.size(); i++) {
                if (((float) unitList.get(i).getBaseAttack() / unitList.get(i).getCost()) > atckToCoast & !limitedUnitTypeList.contains(unitList.get(i).getUnitType())) {
                    atckToCoast = (float) unitList.get(i).getBaseAttack() / unitList.get(i).getCost();
                    index = i;
                }
            }
            // добаляем юниты в список по 1 условию
            while (pointsLeft >= minCoast & allowedUnits != 0) {
                armyList.add(unitList.get(index));
                pointsLeft = pointsLeft - unitList.get(index).getCost();
                allowedUnits--;
                if (allowedUnits == 0)
                    limitedUnitTypeList.add(unitList.get(index).getUnitType());
            }
            // 2 условие выбора
            for (int i = 0; i < unitList.size(); i++) {
                if (((float) unitList.get(i).getHealth() / unitList.get(i).getCost()) > healToCoast & !limitedUnitTypeList.contains(unitList.get(i).getUnitType())) {
                    healToCoast = (float) unitList.get(i).getHealth() / unitList.get(i).getCost();
                    index = i;
                }
            }
            // добаляем юниты в список по 2 условию
            if (allowedUnits == 0)
                allowedUnits = 11;
            while (pointsLeft >= minCoast & allowedUnits != 0) {
                armyList.add(unitList.get(index));
                pointsLeft = pointsLeft - unitList.get(index).getCost();
                allowedUnits--;
            }
        }

        return new Army(armyList);
    }
}