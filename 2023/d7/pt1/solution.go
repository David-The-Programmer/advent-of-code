package main

import (
	"bufio"
	"fmt"
	"log"
	"math"
	"os"
	"sort"
	"strconv"
	"strings"
)

type hand struct {
	cardLabels string
	bid        int
}

func main() {
	cardLabels := "AKQJT98765432"
	cardLabelToStrength := map[string]int{}
	for i, cl := range cardLabels {
		cardLabelToStrength[string(cl)] = len(cardLabels) - i
	}

	path, err := os.Getwd()
	if err != nil {
		log.Fatal(err)
	}
	file, err := os.Open(path + "/d7/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	hands := []hand{}
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		handData := strings.Split(scanner.Text(), " ")
		cardLabels := handData[0]
		bid, err := strconv.Atoi(handData[1])
		if err != nil {
			log.Panic(err)
		}
		hands = append(hands, hand{cardLabels: cardLabels, bid: bid})
	}
	// sort hands in descending order of rank, based on ranking criterias mentioned
	sort.Slice(hands, func(i, j int) bool {
		handA := hands[i]
		handARank := typeRank(handA)
		handB := hands[j]
		handBRank := typeRank(handB)

		if handARank != handBRank {
			return handARank > handBRank
		}

		for i := range handA.cardLabels {
			if handA.cardLabels[i] != handB.cardLabels[i] {
				handARank = cardLabelToStrength[string(handA.cardLabels[i])]
				handBRank = cardLabelToStrength[string(handB.cardLabels[i])]
				break
			}
		}
		return handARank > handBRank
	})

	totalWinnings := 0
	for i := range hands {
		totalWinnings += (len(hands) - i) * hands[i].bid
	}
	fmt.Println(totalWinnings)

}

func typeRank(h hand) int {
	handRank := 0
	handACardLabelCount := map[string]int{}

	for _, label := range h.cardLabels {
		l := string(label)
		if _, ok := handACardLabelCount[l]; !ok {
			handACardLabelCount[l] = 1
			continue
		}
		handACardLabelCount[l]++
	}

	if len(handACardLabelCount) == 1 {
		handRank = 7
	} else if len(handACardLabelCount) == 2 {
		for _, count := range handACardLabelCount {
			if count == 4 || count == 1 {
				handRank = 6
			} else {
				handRank = 5
			}
		}
	} else if len(handACardLabelCount) == 3 {
		maxLabelCount := math.MinInt
		for _, count := range handACardLabelCount {
			if maxLabelCount < count {
				maxLabelCount = count
			}
		}
		if maxLabelCount == 3 {
			handRank = 4
		} else {
			handRank = 3
		}

	} else if len(handACardLabelCount) == 4 {
		handRank = 2
	} else {
		handRank = 1
	}
	return handRank
}

// Five of a kind, e.g AAAAA -> always 1 unique label

// Four of a kind, e.g AAAAB -> always 2 unique labels,
// 1 label will always have 4 cards,
// the other will always have 1 card

// Full house, e.g 23332 -> always 2 unique labels,
// 1 label will always have 3 cards,
// the other will always have 2 cards

// Three of a kind, e.g TTT98 -> always 3 unique labels,
// 1 label will always have 3 cards,
// the other 2 labels will always have 1 card each

// Two pair, e.g 23432 -> always 3 unique labels,
// 1st label will always have 2 cards,
// 2nd label will always have 2 cards,
// last label will always have 1 card

// One pair, e.g A23A4 -> always 4 unique labels,
// 1st label will always have 2 cards,
// other 3 cards each will have label different from the 1st label and each others

// High card, e.g, 23456 -> always 5 unique labels
