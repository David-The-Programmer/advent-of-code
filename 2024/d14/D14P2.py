def main():
    seconds = 0
    while True:
        pos_to_num_robots = {}
        f = open("input.txt", "r")
        while True:
            line = f.readline()
            if(line == ""):
                break
            pos_and_vel = line.split(" ")
            pos = list(map(lambda p: int(p), pos_and_vel[0].split("=")[1].split(",")))
            vel = list(map(lambda p: int(p), pos_and_vel[1].split("=")[1].split(",")))
            width = 101
            height = 103
            pos[0] = (pos[0] + seconds * vel[0]) % width
            pos[1] = (pos[1] + seconds * vel[1]) % height

            if(tuple(pos) in pos_to_num_robots):
                pos_to_num_robots[tuple(pos)] += 1
            else:
                pos_to_num_robots[tuple(pos)] = 1

        all_one = True
        for value in pos_to_num_robots.values():
            if value != 1:
                all_one = False
                break

        if(all_one):
            for r in range(height):
                for c in range(width):
                    if((r, c) in pos_to_num_robots):
                        print(pos_to_num_robots[(r, c)], end="")
                    else:
                        print(".", end="")
                print("")
            print("---------------" + str(seconds) + "----------------")
            break

        seconds += 1


if __name__ == "__main__":
    main()
