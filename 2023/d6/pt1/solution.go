package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

func main() {
	path, err := os.Getwd()
	if err != nil {
		log.Fatal(err)
	}
	file, err := os.Open(path + "/d6/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	scanner := bufio.NewScanner(file)
	times := []int{}
	distances := []int{}
	if scanner.Scan() {
		timesLine := strings.TrimSpace(strings.Split(scanner.Text(), ":")[1])
		timesStrs := strings.Split(timesLine, " ")
		for _, s := range timesStrs {
			if s != "" {
				t, err := strconv.Atoi(s)
				if err != nil {
					log.Panic(err)
				}
				times = append(times, t)
			}
		}
	}
	if scanner.Scan() {
		distLine := strings.TrimSpace(strings.Split(scanner.Text(), ":")[1])
		distStrs := strings.Split(distLine, " ")
		for _, s := range distStrs {
			if s != "" {
				d, err := strconv.Atoi(s)
				if err != nil {
					log.Panic(err)
				}
				distances = append(distances, d)
			}
		}
	}
	numWays := 1
	for i, t := range times {
		beatRecordCount := 0
		for j := 0; j < t; j++ {
			dist := j * (t - j)
			if dist > distances[i] {
				beatRecordCount++
			}
		}
		numWays *= beatRecordCount
	}
	fmt.Println(numWays)
}
