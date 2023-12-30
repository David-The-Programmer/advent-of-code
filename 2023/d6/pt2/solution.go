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
	time := 0
	distance := 0
	if scanner.Scan() {
		timeLine := strings.TrimSpace(strings.Split(scanner.Text(), ":")[1])
		timeStrs := strings.Split(timeLine, " ")
		timeStr := ""
		for _, s := range timeStrs {
			timeStr += s
		}
		t, err := strconv.Atoi(timeStr)
		if err != nil {
			log.Panic(err)
		}
		time = t
	}
	if scanner.Scan() {
		distLine := strings.TrimSpace(strings.Split(scanner.Text(), ":")[1])
		distStrs := strings.Split(distLine, " ")
		distStr := ""
		for _, s := range distStrs {
			distStr += s
		}
		d, err := strconv.Atoi(distStr)
		if err != nil {
			log.Panic(err)
		}
		distance = d
	}

	beatRecordCount := 0
	for j := 0; j < time; j++ {
		dist := j * (time - j)
		if dist > distance {
			beatRecordCount++
		}
	}
	fmt.Println(beatRecordCount)
}
