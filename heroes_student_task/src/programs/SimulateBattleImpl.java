package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog; // Позволяет логировать. Использовать после каждой атаки юнита

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {
        // Ваше решение Этот метод осуществляет симуляцию боя между армией игрока и армией компьютера.
        // Цель метода — провести бой, следуя установленным правилам.
        //
        // Симуляция происходит следующим образом:
        // 1   На каждом раунде юниты сортируются по убыванию значения атаки,
        //      чтобы первыми ходили самые сильные.
        // 2   Пока в обеих армиях есть живые юниты, они атакуют друг друга по очереди.
        // 3   Если у одной из армий заканчиваются юниты,
        //      она ожидает завершения ходов оставшихся юнитов противника.
        // 4   Когда все юниты походили, раунд завершается, и начинается следующий.
        //    Юниты, которые погибли (значение поля isAlive — false) и ещё не походили, исключаются из очередей в момент их смерти, и очереди хода пересчитываются.
        //    Если количество юнитов в армиях становится разным из-за потерь, очерёдность ходов может измениться.
        //    Юниты атакуют друг друга с помощью метода unit.getProgram().attack(), который возвращает цель атаки (юнит противника) или null, если цель не найдена.
        //    После каждой атаки необходимо вывести лог с помощью метода printBattleLog.printBattleLog(unit, target), где unit — атакующий юнит, а target — цель атаки.
        // 5   Симуляция завершается, когда у одной из армий не остаётся живых юнитов, способных сделать ход.

        // Здесь:
        //    Параметр playerArmy — объект армии игрока, содержащий список её юнитов.
        //    Параметр computerArmy — объект армии компьютера, содержащий список её юнитов.
        //    Алгоритмическая сложность данного алгоритма должна быть (O(n2⋅logn)) или лучше,
        //    если принять, что метод атаки юнита работает за O(1), где n — общее количество юнитов в армии.


        // создаём отсортированные списки по убыванию атаки
        List<Unit> sortedPlayerArmy = playerArmy.getUnits().stream().sorted(Comparator.comparing(Unit::getBaseAttack).reversed()).toList();
        List<Unit> sortedComputerArmy = computerArmy.getUnits().stream().sorted(Comparator.comparing(Unit::getBaseAttack).reversed()).toList();

        int stepCounter = 0;
        int playerArmySize = sortedPlayerArmy.size();
        int computerArmySize = sortedComputerArmy.size();

        do {
            // атака игрока
            if (playerArmySize - stepCounter != 0) {
                printBattleLog.printBattleLog(sortedPlayerArmy.get(stepCounter), sortedPlayerArmy.get(stepCounter).getProgram().attack());
                // если в армии противника юнита, пересчитываем общее количество юнитов
                if (sortedComputerArmy.removeIf(unit -> !unit.isAlive())) {
                    computerArmySize = sortedComputerArmy.size();
                }
            }

            // атака компьютера
            if (computerArmySize - stepCounter != 0) {
                printBattleLog.printBattleLog(sortedComputerArmy.get(stepCounter), sortedComputerArmy.get(stepCounter).getProgram().attack());
                // если в армии противника юнита, пересчитываем общее количество юнитов
                if (sortedPlayerArmy.removeIf(unit -> !unit.isAlive())) {
                    playerArmySize = sortedPlayerArmy.size();
                }
            }
            // добавляем счёт хода и проверяем остался ли кто то кто не сходил в разных коммандах,
            // если все сходили счётчик сбрасывается
            stepCounter++;
            if (stepCounter > playerArmySize && stepCounter > computerArmySize)
                stepCounter = 0;
        } while (sortedComputerArmy.isEmpty() || sortedPlayerArmy.isEmpty());

    }
}