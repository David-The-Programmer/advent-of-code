def main():
    f = open("input.txt", "r")
    directions = {
        "<": (-1, 0),
        ">": (1, 0),
        "^": (0, -1),
        "v": (0, 1),
    }
    map = []
    robot_pos = []
    while True:
        line = f.readline()
        if(line == "\n"):
            break
        map.append(list(line.strip()))
    for row in range(len(map)):
        for col in range(len(map[0])):
            if(map[row][col] == "@"):
                robot_pos = [col, row]
                break
        if(len(robot_pos) != 0):
            break
    for row in map:
        print(row)
    print()
    while True:
        line = f.readline()
        if(line == ""):
            break
        movements = list(line.strip())
        for m in movements:
            new_pos = [robot_pos[0] + directions[m][0], robot_pos[1] + directions[m][1]]
            if map[new_pos[1]][new_pos[0]] == "#":
                continue
            if map[new_pos[1]][new_pos[0]] == ".":
                map[robot_pos[1]][robot_pos[0]] = "."
                robot_pos = [new_pos[0], new_pos[1]]
                map[robot_pos[1]][robot_pos[0]] = "@"
                continue
            if map[new_pos[1]][new_pos[0]] == "O":
                pos = [new_pos[0] + directions[m][0], new_pos[1] + directions[m][1]]
                new_pos = [[new_pos[0], new_pos[1]]]
                while True:
                    new_pos.append([pos[0], pos[1]])
                    if map[pos[1]][pos[0]] == "." or map[pos[1]][pos[0]] == "#":
                        break
                    pos = [pos[0] + directions[m][0], pos[1] + directions[m][1]]
                if map[pos[1]][pos[0]] == "#":
                    continue
                if map[pos[1]][pos[0]] == ".":
                    for i in range(len(new_pos) - 1, -1, -1):
                        if i == 0:
                            break
                        tmp = map[new_pos[i][1]][new_pos[i][0]]
                        map[new_pos[i][1]][new_pos[i][0]] = map[new_pos[i - 1][1]][new_pos[i - 1][0]]
                        map[new_pos[i - 1][1]][new_pos[i - 1][0]] = tmp
                    map[robot_pos[1]][robot_pos[0]] = "."
                    robot_pos = [new_pos[0][0], new_pos[0][1]]
                    map[robot_pos[1]][robot_pos[0]] = "@"

        for row in map:
            print(row)
        print()
    sum = 0
    for row in range(len(map)):
        for col in range(len(map[0])):
            if(map[row][col] == "O"):
                sum += col + 100 * row

    print(sum)

if __name__ == "__main__":
    main()
