def main():
    f = open("input.txt", "r")
    numRobotsTopLeft = 0
    numRobotsTopRight = 0
    numRobotsBottomLeft= 0
    numRobotsBottomRight = 0
    while True:
        line = f.readline()
        if(line == ""):
            break
        pos_and_vel = line.split(" ")
        pos = list(map(lambda p: int(p), pos_and_vel[0].split("=")[1].split(",")))
        vel = list(map(lambda p: int(p), pos_and_vel[1].split("=")[1].split(",")))
        width = 101
        height = 103
        seconds = 100
        pos[0] = (pos[0] + seconds * vel[0]) % width
        pos[1] = (pos[1] + seconds * vel[1]) % height

        widthMidIndex = (width + 1) / 2 - 1
        heightMidIndex = (height + 1) / 2 - 1
        if(pos[0] < widthMidIndex and pos[1] < heightMidIndex):
            numRobotsTopLeft += 1
            continue
        if(pos[0] > widthMidIndex and pos[1] < heightMidIndex):
            numRobotsTopRight += 1
            continue
        if(pos[0] < widthMidIndex and pos[1] > heightMidIndex):
            numRobotsBottomLeft += 1
            continue
        if(pos[0] > widthMidIndex and pos[1] > heightMidIndex):
            numRobotsBottomRight += 1
            continue

    safetyFactor =  numRobotsTopLeft * numRobotsTopRight * numRobotsBottomLeft * numRobotsBottomRight 
    print(safetyFactor)

if __name__ == "__main__":
    main()
