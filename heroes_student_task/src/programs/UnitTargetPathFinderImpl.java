package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.Edge;
import com.battle.heroes.army.programs.UnitTargetPathFinder;

import java.util.*;

public class UnitTargetPathFinderImpl implements UnitTargetPathFinder {
    @Override
    public List<Edge> getTargetPath(Unit attackUnit, Unit targetUnit, List<Unit> existingUnitList) {
        // Ваше решение отвечает за поиск наикратчайшего пути между атакующим и атакуемым юнитом
        // Метод определяет кратчайший маршрут между атакующим и атакуемым юнитом и возвращает его
        // в виде списка объектов, содержащих координаты каждой точки данного кратчайшего пути.
        //
        // Цель данного метода — найти кратчайший путь между атакующим и атакуемым юнитами.
        // То есть для атакующего юнита с координатами x=1 и y=2 и атакуемого юнита x=0 и y=0
        // результатом станет список [Edge(1, 2), Edge (1, 1), Edge (1,0)].
        //
        // Для определения кратчайшего пути рекомендуем использовать один из алгоритмов теории графов.
        //
        // Здесь:
        //    Параметр attackUnit — юнит, который атакует.
        //    Параметр targetUnit — юнит, который подвергается атаке.
        //    Параметр existingUnitList — список всех существующих юнитов на данный момент.
        //    Возвращаемое значение — список объектов Edge, то есть координат клеток пути от атакующего юнита
        //    до атакуемого юнита включительно. Если маршрут не найден — возвращает пустой список.
        //
        // Алгоритмическая сложность данного метода составляет O((WIDTH⋅HEIGHT)logWIDTH⋅HEIGHT) или лучше, где:
        //    WIDTH — ширина игрового поля (27);
        //    HEIGHT — высота игрового поля (21).
        //
        // Алгоритм, представленный в коде, использует один из алгоритмов теории графов для нахождения
        // кратчайшего пути на поле с препятствиями, где некоторые клетки заняты юнитами.
        // Основные шаги включают инициализацию структуры данных для хранения расстояний,
        // обработку соседних клеток и определение пути. Допускается движение по диагонали.

        List<Edge> closedCells = List.of();
        // задаём координаты рассматривавемой ячейки
        Edge activeCell = new Edge(attackUnit.getxCoordinate(), attackUnit.getyCoordinate());

        while (targetDistance(activeCell, targetUnit) != 0) {
            activeCell = (cellToGo(activeCell, targetUnit, existingUnitList));
            if (!activeCell.equals(new Edge(-1, -1)))
                closedCells.add(activeCell);
            else {
                List<Edge> emptyList = new ArrayList<>();
                return emptyList;
            }
        }
        return closedCells;
    }

    private Edge cellToGo(Edge activeCell, Unit targetUnit, List<Unit> existingUnitList) {
        List<Edge> openCells = new ArrayList<>();
        List<Integer> weighst = new ArrayList<Integer>();
        int newX, newY, wheit = 0;
        //рассматриваем соседние ячейки и присвеваем им веса
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                wheit = 0;
                newX = activeCell.getX() + i;
                newY = activeCell.getY() + j;
                // провеврка что ячейка сободна
                int finalNewX = newX;
                int finalNewY = newY;
                if (existingUnitList.stream().anyMatch(unit -> (unit.getxCoordinate() == finalNewX) && (unit.getyCoordinate() == finalNewY))) {
                    continue;
                } else {
                    // проверка что ячейки в границах поля
                    if ((newX > -1) & (newY > -1) & (newX < 28) & (newY < 22)) {
                        openCells.add(new Edge(newX, newY));
                        //расстанока весов для ячеек по ортам
                        if (i == 0 || j == 0) {
                            wheit = 10 + targetDistance(new Edge(newX, newY), targetUnit);
                            weighst.add(wheit);
                        }
                        //расстанока весов для ячеек по диагонали
                        else {
                            wheit = 14 + targetDistance(new Edge(newX, newY), targetUnit);
                            weighst.add(wheit);
                        }
                    }
                }
            }
        }
        //если из текущей ячейки нет ввозможных ходов
        if (openCells.isEmpty()) {
            return new Edge(-1, -1);
        }
        //выбираем индекс ячейки с меньшим весом
        int index = weighst.indexOf(Collections.min(weighst));
        // возвращаем ячейку с меньшим весом
        return openCells.get(index);
    }

    private int targetDistance(Edge cell, Unit targetCell) {
        int xDistance = cell.getX() - targetCell.getxCoordinate();
        int yDistance = cell.getY() - targetCell.getyCoordinate();
        return (Math.abs(xDistance) + Math.abs(yDistance)) * 10;
    }
}