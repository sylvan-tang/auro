package domain

import assocv1 "github.com/sylvan/auro/apis/assoc/v1"

func GetRevertType(aType assocv1.AssocType) assocv1.AssocType {
	if aType%2 == 0 {
		return aType - 1
	}
	return aType + 1
}
