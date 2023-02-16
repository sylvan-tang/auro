package runtime

import (
	"path"
	runtimelib "runtime"
	"strings"
)

var (
	packagePath       string
	rootStep          = 3
	TestResourcesPath = path.Join(GetRuntimePackagePath(4), "resources")
)

func init() {
	packagePath = GetRuntimePackagePath(rootStep)
}

// GetRuntimeFileName return caller's base filename.
func GetRuntimeFileName() string {
	_, filename, _, _ := runtimelib.Caller(1)
	return path.Base(filename)
}

// GetRuntimePackagePath return caller package path, skip last rootStep dirs to caller's file.
func GetRuntimePackagePath(rootStep int) string {
	_, filename, _, _ := runtimelib.Caller(1)
	rootPath := filename
	for i := 0; i < rootStep; i++ {
		rootPath = path.Dir(rootPath)
	}
	return rootPath
}

// GetRuntimeFunctionNames return callers method that is wrritten in the repo.
func GetRuntimeFunctionNames() []string {
	pc := make([]uintptr, 15)
	n := runtimelib.Callers(2, pc)
	frames := runtimelib.CallersFrames(pc[:n])
	var funcNames []string
	for {
		frame, ok := frames.Next()
		if !ok {
			break
		}
		if !strings.HasPrefix(frame.File, packagePath) {
			continue
		}
		funcNames = append(funcNames, frame.Function[strings.LastIndex(frame.Function, ".")+1:])
	}
	return funcNames
}

func GetResourcesDirForFunction() string {
	return path.Join(TestResourcesPath, GetRuntimeFunctionNames()[1])
}
