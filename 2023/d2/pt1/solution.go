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
	file, err := os.Open(path + "/d2/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	scanner := bufio.NewScanner(file)
	idSum := 0
	gameID := 0
	for scanner.Scan() {
		gameID++
		line := scanner.Text()
		s := strings.Split(line, ":")[1]
		if err != nil {
			log.Fatal(err)
		}
		picks := strings.Split(s, ";")
		invalidPick := false
		for _, pick := range picks {
			colourCounts := strings.Split(pick, ", ")
			for _, colourCount := range colourCounts {
				colourCount = strings.TrimSpace(colourCount)
				s := strings.Split(colourCount, " ")
				count, err := strconv.Atoi(s[0])
				if err != nil {
					log.Fatal(err)
				}
				colour := s[1]
				invalidPick = (colour == "red" && count > 12) || (colour == "green" && count > 13) || (colour == "blue" && count > 14)
				if invalidPick {
					break
				}
			}
			if invalidPick {
				break
			}
		}
		if invalidPick {
			continue
		} else {
			idSum += gameID
		}
	}
	fmt.Println(idSum)
}
