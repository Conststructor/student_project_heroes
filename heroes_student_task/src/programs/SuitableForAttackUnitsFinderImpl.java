package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        // Ваше решение и отвечает за создание перечня подходящих для атаки юнитов.
        // Метод определяет список юнитов, подходящих для атаки, для атакующего юнита одной из армий.
        // Цель метода — исключить ненужные попытки найти кратчайший путь между юнитами,
        // которые не могут атаковать друг друга.
        //
        // Подходящий юнит для атаки для атакующей армии компьютера — это юнит армии игрока,
        // который не закрыт справа (по координате y) другим юнитом армии игрока.
        //
        // Подходящий юнит для атаки для атакующей армии игрока — это юнит армии компьютера,
        // который не закрыт слева (по координате y) другим юнитом армии компьютера.
        // То есть слева от юнита в соседней клетке находится другой юнит.
        //
        //В нём
        //
        // Параметр unitsByRow — трёхслойный массив юнитов противника. Для юнита из атакующей армии компьютера
        // эти юниты находятся на координатах 24..26 по оси x. Для армии игрока они располагаются
        // на координатах 0..2 по оси x (фактически, это юниты армии компьютера).
        //
        // Параметр isLeftArmyTarget — параметр, который указывает, юниты какой армии подвергаются атаке.
        // Если значение true, то атаке подвергаются юниты армии компьютера (левая армия);
        // если false — юниты армии игрока (правая армия).
        //
        // Возвращаемое значение — метод возвращает список юнитов, подходящих для атаки, для юнита атакующей армии.
        //
        // Алгоритмическая сложность данного метода должна быть O(n⋅m) или лучше, что означает
        // линейную сложность на двумерной плоскости, где n — количество юнитов в ряду, а m — количество рядов.
        // Так как количество рядов фиксировано и равно трём, алгоритм фактически должен иметь
        // линейную сложность O(n) или лучше.

        List<Unit> atackList = new ArrayList<>();
        if (isLeftArmyTarget == true) {
            for (int i = 2; i >= 0; i--) {
                //проверка есть ли юниты в данном ряду
                int row = i;
                if (unitsByRow.stream().flatMap(List::stream).anyMatch(unit -> unit.getxCoordinate() == row)) {
                    // запись в лист всех юнитов в данном ряду
                    atackList = unitsByRow.stream().flatMap(List::stream).filter(unit -> unit.getxCoordinate() == row).toList();
                    break;
                }
            }
        }
        if (isLeftArmyTarget == false) {
            for (int i = 24; i <= 26; i++) {
                //проверка есть ли юниты в данном ряду
                int row = i;
                if (unitsByRow.stream().flatMap(List::stream).anyMatch(unit -> unit.getxCoordinate() == row)) {
                    // запись в лист всех юнитов в данном ряду
                    atackList = unitsByRow.stream().flatMap(List::stream).filter(unit -> unit.getxCoordinate() == row).toList();
                    break;
                }
            }
        }
            return atackList;
    }
}