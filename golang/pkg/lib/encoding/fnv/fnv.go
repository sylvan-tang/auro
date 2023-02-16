package fnv

import (
	"hash/fnv"

	"github.com/gogf/gf/frame/g"
)

func FNV32a(input []byte) uint32 {
	output := fnv.New32a()
	_, err := output.Write(input)
	if err != nil {
		g.Log().Info("FNV32a write err: ", err)
		return uint32(0)
	}
	return output.Sum32()
}
