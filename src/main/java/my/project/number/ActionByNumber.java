package my.project.number;

import java.util.*;
import java.util.stream.Collectors;

public class ActionByNumber {
    public List<List<String>> findGroups(List<String> lines) {
        List<Map<String, Integer>> numbersToGroupsNumbers = new ArrayList<>(); //[позиция числа:{число:номер группы}]
        List<List<String>> linesGroups = new ArrayList<>(); //[номер группы:[строки группы]]
        Map<Integer, Integer> mergedGroupToFinalGroup = new HashMap<>(); //{номер слитой группы:номер группы в которую слили}
        for (String line : lines) {
            String[] numbers = line.split(";");
            TreeSet<Integer> foundInGroups = new TreeSet<>();
            List<NewNumber> newNumbers = new ArrayList<>();

            boolean flag = validNumber(numbers, foundInGroups, newNumbers, numbersToGroupsNumbers, mergedGroupToFinalGroup);

            int groupNumber;

            if (flag) {
                if (foundInGroups.isEmpty()) {
                    groupNumber = linesGroups.size();
                    linesGroups.add(new ArrayList<>());
                } else {
                    groupNumber = foundInGroups.first();
                }

                newNumbers.forEach(number -> numbersToGroupsNumbers.get(number.position).put(number.number, groupNumber));

                for (int mergeGroupNumber : foundInGroups) {
                    if (mergeGroupNumber != groupNumber) {
                        mergedGroupToFinalGroup.put(mergeGroupNumber, groupNumber);
                        linesGroups.get(groupNumber).addAll(linesGroups.get(mergeGroupNumber));
                        linesGroups.set(mergeGroupNumber, null);
                    }
                }

                linesGroups.get(groupNumber).add(line);
            }
        }

        linesGroups.removeAll(Collections.singleton(null));

        List<List<String>> finalGroups = linesGroups.stream()
                .map(g -> g.stream().distinct().collect(Collectors.toList()))
                .collect(Collectors.toList());

        return finalGroups.stream()
                .sorted(Comparator.comparing(
                        List::size, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private boolean validNumber(String[] numbers, TreeSet<Integer> foundInGroups, List<NewNumber> newNumbers,
                                List<Map<String, Integer>> numbersToGroupsNumbers,
                                Map<Integer, Integer> mergedGroupToFinalGroup) {
        boolean flag = true;
        for (int i = 0; i < numbers.length; i++) {
            String number = numbers[i];
            if (number.contains("\"\"") && number.getBytes().length != 2) {
                flag = false;
                break;
            }

            if (numbersToGroupsNumbers.size() == i) {
                numbersToGroupsNumbers.add(new HashMap<>());
            }

            Map<String, Integer> numberToGroupNumber = numbersToGroupsNumbers.get(i);
            Integer numberGroupNumber = numberToGroupNumber.get(number);

            if (number.getBytes().length == 2) {
                numberGroupNumber = null;
            }

            if (numberGroupNumber != null) {
                while (mergedGroupToFinalGroup.containsKey(numberGroupNumber)) {
                    numberGroupNumber = mergedGroupToFinalGroup.get(numberGroupNumber);
                }

                foundInGroups.add(numberGroupNumber);
            } else {
                newNumbers.add(new NewNumber(number, i));
            }
        }
        return flag;
    }
}
