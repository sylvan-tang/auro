package leetcode

import (
	"testing"

	. "github.com/smartystreets/goconvey/convey"
)

type node struct {
	word     string
	children []*node
	depth    int
}

func newNode(word string, children []*node) *node {
	depth := 0
	for _, child := range children {
		depth = child.depth + 1
		break
	}
	return &node{
		word:     word,
		children: children,
		depth:    depth,
	}
}

func (n *node) addChild(child *node) bool {
	if child.depth+1 > n.depth {
		return false
	} else if child.depth+1 == n.depth {
		n.children = append(n.children, child)
	} else {
		n.children = []*node{child}
		n.depth = child.depth + 1
	}
	return true
}

func findLadders(beginWord string, endWord string, wordList []string) [][]string {
	if compareWord(beginWord, endWord) == 1 {
		return [][]string{{beginWord, endWord}}
	}
	metrics := [][]int{}
	endWordIndex := -1
	firstLine := []int{}
	lastLine := []int{}
	hasBeginWord := false
	wordToIndex := make(map[string]int)
	for i, w := range wordList {
		wordToIndex[w] = i
		c := compareWord(endWord, w)
		if c == 0 {
			endWordIndex = i
		}
		firstLine = append(firstLine, c)
		c = compareWord(beginWord, w)
		if c < 2 {
			hasBeginWord = true
		}
		lastLine = append(lastLine, c)
	}
	if endWordIndex < 0 || !hasBeginWord {
		return [][]string{}
	}
	for i, wi := range wordList {
		line := []int{}
		if firstLine[i] == 0 {
			metrics = append(metrics, firstLine)
			continue
		}
		for j, wj := range wordList {
			if i > j {
				line = append(line, metrics[j][i])
			} else if i == j {
				line = append(line, 0)
			} else {
				line = append(line, compareWord(wi, wj))
			}
		}
		metrics = append(metrics, line)
	}
	stacks := []*node{newNode(endWord, []*node{})}
	vistedNode := make(map[string]*node)

	for len(stacks) > 0 {
		item := stacks[0]
		stacks = stacks[1:]
		vistedN, ok := vistedNode[item.word]
		if ok {
			for _, child := range item.children {
				vistedN.addChild(child)
			}
			vistedNode[item.word] = vistedN
			continue
		}
		vistedNode[item.word] = item
		line := metrics[wordToIndex[item.word]]
		for i, count := range line {
			if count == 1 {
				stacks = append(stacks, newNode(wordList[i], []*node{item}))
			}
		}

	}
	var beginNode *node
	for j, c := range lastLine {
		if c > 1 {
			continue
		}
		w := wordList[j]
		n, ok := vistedNode[w]
		if !ok {
			continue
		}
		if c == 0 {
			beginNode = n
			break
		}
		if beginNode == nil {
			beginNode = newNode(beginWord, []*node{n})
		} else {
			beginNode.addChild(n)
		}
	}
	return vistNode(beginNode)
}

func vistNode(n *node) [][]string {
	if n == nil {
		return [][]string{}
	}
	if n.depth == 0 {
		return [][]string{{n.word}}
	}
	outputs := [][]string{}
	for _, child := range n.children {
		for _, line := range vistNode(child) {
			output := []string{n.word}
			output = append(output, line...)
			outputs = append(outputs, output)
		}
	}
	return outputs
}

func compareWord(srcWord, targetWord string) int {
	output := 0
	for i := range srcWord {
		if srcWord[i] != targetWord[i] {
			output++
		}
	}
	return output
}

func TestFindLadders(t *testing.T) {
	Convey("Testings ", t, func(c C) {
		testCases := []struct {
			beginWord string
			endWord   string
			wordList  []string
			output    [][]string
		}{
			{
				beginWord: "hit",
				endWord:   "cog",
				wordList:  []string{"hot", "dot", "dog", "lot", "log", "cog"},
				output: [][]string{
					{"hit", "hot", "dot", "dog", "cog"},
					{"hit", "hot", "lot", "log", "cog"},
				},
			},
			{
				beginWord: "cet",
				endWord:   "ism",
				wordList:  []string{"kid", "tag", "pup", "ail", "tun", "woo", "erg", "luz", "brr", "gay", "sip", "kay", "per", "val", "mes", "ohs", "now", "boa", "cet", "pal", "bar", "die", "war", "hay", "eco", "pub", "lob", "rue", "fry", "lit", "rex", "jan", "cot", "bid", "ali", "pay", "col", "gum", "ger", "row", "won", "dan", "rum", "fad", "tut", "sag", "yip", "sui", "ark", "has", "zip", "fez", "own", "ump", "dis", "ads", "max", "jaw", "out", "btu", "ana", "gap", "cry", "led", "abe", "box", "ore", "pig", "fie", "toy", "fat", "cal", "lie", "noh", "sew", "ono", "tam", "flu", "mgm", "ply", "awe", "pry", "tit", "tie", "yet", "too", "tax", "jim", "san", "pan", "map", "ski", "ova", "wed", "non", "wac", "nut", "why", "bye", "lye", "oct", "old", "fin", "feb", "chi", "sap", "owl", "log", "tod", "dot", "bow", "fob", "for", "joe", "ivy", "fan", "age", "fax", "hip", "jib", "mel", "hus", "sob", "ifs", "tab", "ara", "dab", "jag", "jar", "arm", "lot", "tom", "sax", "tex", "yum", "pei", "wen", "wry", "ire", "irk", "far", "mew", "wit", "doe", "gas", "rte", "ian", "pot", "ask", "wag", "hag", "amy", "nag", "ron", "soy", "gin", "don", "tug", "fay", "vic", "boo", "nam", "ave", "buy", "sop", "but", "orb", "fen", "paw", "his", "sub", "bob", "yea", "oft", "inn", "rod", "yam", "pew", "web", "hod", "hun", "gyp", "wei", "wis", "rob", "gad", "pie", "mon", "dog", "bib", "rub", "ere", "dig", "era", "cat", "fox", "bee", "mod", "day", "apr", "vie", "nev", "jam", "pam", "new", "aye", "ani", "and", "ibm", "yap", "can", "pyx", "tar", "kin", "fog", "hum", "pip", "cup", "dye", "lyx", "jog", "nun", "par", "wan", "fey", "bus", "oak", "bad", "ats", "set", "qom", "vat", "eat", "pus", "rev", "axe", "ion", "six", "ila", "lao", "mom", "mas", "pro", "few", "opt", "poe", "art", "ash", "oar", "cap", "lop", "may", "shy", "rid", "bat", "sum", "rim", "fee", "bmw", "sky", "maj", "hue", "thy", "ava", "rap", "den", "fla", "auk", "cox", "ibo", "hey", "saw", "vim", "sec", "ltd", "you", "its", "tat", "dew", "eva", "tog", "ram", "let", "see", "zit", "maw", "nix", "ate", "gig", "rep", "owe", "ind", "hog", "eve", "sam", "zoo", "any", "dow", "cod", "bed", "vet", "ham", "sis", "hex", "via", "fir", "nod", "mao", "aug", "mum", "hoe", "bah", "hal", "keg", "hew", "zed", "tow", "gog", "ass", "dem", "who", "bet", "gos", "son", "ear", "spy", "kit", "boy", "due", "sen", "oaf", "mix", "hep", "fur", "ada", "bin", "nil", "mia", "ewe", "hit", "fix", "sad", "rib", "eye", "hop", "haw", "wax", "mid", "tad", "ken", "wad", "rye", "pap", "bog", "gut", "ito", "woe", "our", "ado", "sin", "mad", "ray", "hon", "roy", "dip", "hen", "iva", "lug", "asp", "hui", "yak", "bay", "poi", "yep", "bun", "try", "lad", "elm", "nat", "wyo", "gym", "dug", "toe", "dee", "wig", "sly", "rip", "geo", "cog", "pas", "zen", "odd", "nan", "lay", "pod", "fit", "hem", "joy", "bum", "rio", "yon", "dec", "leg", "put", "sue", "dim", "pet", "yaw", "nub", "bit", "bur", "sid", "sun", "oil", "red", "doc", "moe", "caw", "eel", "dix", "cub", "end", "gem", "off", "yew", "hug", "pop", "tub", "sgt", "lid", "pun", "ton", "sol", "din", "yup", "jab", "pea", "bug", "gag", "mil", "jig", "hub", "low", "did", "tin", "get", "gte", "sox", "lei", "mig", "fig", "lon", "use", "ban", "flo", "nov", "jut", "bag", "mir", "sty", "lap", "two", "ins", "con", "ant", "net", "tux", "ode", "stu", "mug", "cad", "nap", "gun", "fop", "tot", "sow", "sal", "sic", "ted", "wot", "del", "imp", "cob", "way", "ann", "tan", "mci", "job", "wet", "ism", "err", "him", "all", "pad", "hah", "hie", "aim"},
				output: [][]string{
					{"cet", "cat", "can", "ian", "inn", "ins", "its", "ito", "ibo", "ibm", "ism"},
					{"cet", "cot", "con", "ion", "inn", "ins", "its", "ito", "ibo", "ibm", "ism"},
				},
			},
			{
				beginWord: "magic",
				endWord:   "pearl",
				wordList:  []string{"flail", "halon", "lexus", "joint", "pears", "slabs", "lorie", "lapse", "wroth", "yalow", "swear", "cavil", "piety", "yogis", "dhaka", "laxer", "tatum", "provo", "truss", "tends", "deana", "dried", "hutch", "basho", "flyby", "miler", "fries", "floes", "lingo", "wider", "scary", "marks", "perry", "igloo", "melts", "lanny", "satan", "foamy", "perks", "denim", "plugs", "cloak", "cyril", "women", "issue", "rocky", "marry", "trash", "merry", "topic", "hicks", "dicky", "prado", "casio", "lapel", "diane", "serer", "paige", "parry", "elope", "balds", "dated", "copra", "earth", "marty", "slake", "balms", "daryl", "loves", "civet", "sweat", "daley", "touch", "maria", "dacca", "muggy", "chore", "felix", "ogled", "acids", "terse", "cults", "darla", "snubs", "boats", "recta", "cohan", "purse", "joist", "grosz", "sheri", "steam", "manic", "luisa", "gluts", "spits", "boxer", "abner", "cooke", "scowl", "kenya", "hasps", "roger", "edwin", "black", "terns", "folks", "demur", "dingo", "party", "brian", "numbs", "forgo", "gunny", "waled", "bucks", "titan", "ruffs", "pizza", "ravel", "poole", "suits", "stoic", "segre", "white", "lemur", "belts", "scums", "parks", "gusts", "ozark", "umped", "heard", "lorna", "emile", "orbit", "onset", "cruet", "amiss", "fumed", "gelds", "italy", "rakes", "loxed", "kilts", "mania", "tombs", "gaped", "merge", "molar", "smith", "tangs", "misty", "wefts", "yawns", "smile", "scuff", "width", "paris", "coded", "sodom", "shits", "benny", "pudgy", "mayer", "peary", "curve", "tulsa", "ramos", "thick", "dogie", "gourd", "strop", "ahmad", "clove", "tract", "calyx", "maris", "wants", "lipid", "pearl", "maybe", "banjo", "south", "blend", "diana", "lanai", "waged", "shari", "magic", "duchy", "decca", "wried", "maine", "nutty", "turns", "satyr", "holds", "finks", "twits", "peaks", "teems", "peace", "melon", "czars", "robby", "tabby", "shove", "minty", "marta", "dregs", "lacks", "casts", "aruba", "stall", "nurse", "jewry", "knuth"},
				output: [][]string{
					{"magic", "manic", "mania", "maria", "maris", "marks", "parks", "perks", "peaks", "pears", "pearl"},
					{"magic", "manic", "mania", "maria", "maris", "paris", "parks", "perks", "peaks", "pears", "pearl"},
					{"magic", "manic", "mania", "maria", "marta", "marty", "marry", "merry", "perry", "peary", "pearl"},
					{"magic", "manic", "mania", "maria", "marta", "marty", "marry", "parry", "perry", "peary", "pearl"},
					{"magic", "manic", "mania", "maria", "marta", "marty", "party", "parry", "perry", "peary", "pearl"},
				},
			},
		}
		for i, tc := range testCases {
			_, _ = c.Printf("case #%d: src=%+v dest=%+v\n", i, tc.beginWord, tc.endWord)
			c.So(tc.output, ShouldResemble, findLadders(tc.beginWord, tc.endWord, tc.wordList))
		}
	})
}
