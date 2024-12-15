from collections import deque

def main():
    f = open("input.txt", "r")
    directions = {
        "<": (-1, 0),
        ">": (1, 0),
        "^": (0, -1),
        "v": (0, 1),
    }
    grid = []
    robot_pos = []
    while True:
        line = f.readline()
        if(line == "\n"):
            break
        s = ""
        for c in line.strip():
            s += widen(c)

        grid.append(list(s))
    for row in range(len(grid)):
        for col in range(len(grid[0])):
            if(grid[row][col] == "@"):
                robot_pos = [col, row]
                break
        if(len(robot_pos) != 0):
            break
    for row in grid:
        print("".join(row))
    print()
    while True:
        line = f.readline()
        if(line == ""):
            break
        movements = list(line.strip())
        for m in movements:
            for row in grid:
                print("".join(row))
            print(m)
            print()
            new_pos = [robot_pos[0] + directions[m][0], robot_pos[1] + directions[m][1]]
            if grid[new_pos[1]][new_pos[0]] == "#":
                continue
            if grid[new_pos[1]][new_pos[0]] == ".":
                grid[robot_pos[1]][robot_pos[0]] = "."
                robot_pos = [new_pos[0], new_pos[1]]
                grid[robot_pos[1]][robot_pos[0]] = "@"
                continue
            if (grid[new_pos[1]][new_pos[0]] == "[" or grid[new_pos[1]][new_pos[0]] == "]") and (m == "^" or m == "v"):
                q = deque()
                visited = []
                if grid[new_pos[1]][new_pos[0]] == "[":
                    q.append([new_pos[0], new_pos[1]])
                    q.append([new_pos[0] + 1, new_pos[1]])
                else:
                    q.append([new_pos[0] - 1, new_pos[1]])
                    q.append([new_pos[0], new_pos[1]])

                found_wall = False
                while len(q) != 0:
                    pos = q.popleft()
                    if pos not in visited:
                        visited.append([pos[0], pos[1]])
                    pos[0] += directions[m][0]
                    pos[1] += directions[m][1]
                    if grid[pos[1]][pos[0]] == "[":
                        q.append([pos[0], pos[1]])
                        q.append([pos[0] + 1, pos[1]])
                    elif grid[pos[1]][pos[0]] == "]":
                        q.append([pos[0] - 1, pos[1]])
                        q.append([pos[0], pos[1]])
                    elif grid[pos[1]][pos[0]] == "#":
                        found_wall = True
                if found_wall:
                    continue
                last_box_y = visited[-1][1]
                while True:
                    last_row = list(filter(lambda x: x[1] == last_box_y, visited))
                    for box in last_row:
                        tmp = grid[box[1] + directions[m][1]][box[0] + directions[m][0]]
                        grid[box[1] + directions[m][1]][box[0] + directions[m][0]] = grid[box[1]][box[0]]
                        grid[box[1]][box[0]] = tmp
                    if last_box_y == new_pos[1]:
                        break
                    last_box_y -= directions[m][1]
                grid[robot_pos[1]][robot_pos[0]] = "."
                robot_pos = [new_pos[0], new_pos[1]]
                grid[robot_pos[1]][robot_pos[0]] = "@"

            if (grid[new_pos[1]][new_pos[0]] == "[" or grid[new_pos[1]][new_pos[0]] == "]") and (m == "<" or m == ">"):
                pos = [new_pos[0] + directions[m][0], new_pos[1] + directions[m][1]]
                new_pos = [[new_pos[0], new_pos[1]]]
                while True:
                    new_pos.append([pos[0], pos[1]])
                    if grid[pos[1]][pos[0]] == "." or grid[pos[1]][pos[0]] == "#":
                        break
                    pos = [pos[0] + directions[m][0], pos[1] + directions[m][1]]
                if grid[pos[1]][pos[0]] == "#":
                    continue
                if grid[pos[1]][pos[0]] == ".":
                    for i in range(len(new_pos) - 1, -1, -1):
                        if i == 0:
                            break
                        tmp = grid[new_pos[i][1]][new_pos[i][0]]
                        grid[new_pos[i][1]][new_pos[i][0]] = grid[new_pos[i - 1][1]][new_pos[i - 1][0]]
                        grid[new_pos[i - 1][1]][new_pos[i - 1][0]] = tmp
                    grid[robot_pos[1]][robot_pos[0]] = "."
                    robot_pos = [new_pos[0][0], new_pos[0][1]]
                    grid[robot_pos[1]][robot_pos[0]] = "@"

    for row in grid:
        print("".join(row))

    print(m)
    print()
    sum = 0
    for row in range(len(grid)):
        for col in range(len(grid[0])):
            if(grid[row][col] == "["):
                sum += col + 100 * row

    print(sum)


def widen(s):
    if s == "#":
        return "##"
    if s == "O":
        return "[]"
    if s == "@":
        return "@."
    return ".."

if __name__ == "__main__":
    main()
