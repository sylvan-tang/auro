package assoc

import (
	"sync"

	"github.com/sylvan/auro/pkg/service/assoc/adapter/impl"
)

var (
	server *Service
	once   sync.Once
)

func GetInstance() *Service {
	var err error
	once.Do(func() {
		server = &Service{}
	})
	if err != nil {
		panic(err)
	}
	return server
}

type Service struct {
	impl.AssocServiceImpl
}
