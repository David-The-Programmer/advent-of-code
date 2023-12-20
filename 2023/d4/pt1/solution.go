package main

import (
	"bufio"
	"fmt"
	"log"
	"math"
	"os"
	"strings"
)

func main() {
	path, err := os.Getwd()
	if err != nil {
		log.Fatal(err)
	}
	file, err := os.Open(path + "/d4/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	scanner := bufio.NewScanner(file)
	pointSum := 0
	for scanner.Scan() {
		line := scanner.Text()
		cardNumsLine := strings.TrimSpace(strings.Split(line, ":")[1])
		cardNums := strings.Split(cardNumsLine, "|")
		winningNumsRaw := strings.Split(strings.TrimSpace(cardNums[0]), " ")
		ownedNumsRaw := strings.Split(strings.TrimSpace(cardNums[1]), " ")

		winningNums := []string{}
		ownedNums := []string{}
		for _, num := range winningNumsRaw {
			if num != "" {
				winningNums = append(winningNums, num)
			}
		}
		for _, num := range ownedNumsRaw {
			if num != "" {
				ownedNums = append(ownedNums, num)
			}
		}
		winNums := map[string]struct{}{}
		for _, winNum := range winningNums {
			winNums[winNum] = struct{}{}
		}
		matchingNums := 0
		for _, ownNum := range ownedNums {
			if _, ok := winNums[ownNum]; ok {
				matchingNums++
			}
		}
		if matchingNums > 0 {
			pointSum += int(math.Pow(2, float64(matchingNums-1)))
		}
	}

	fmt.Println(pointSum)
}
