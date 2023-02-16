package assoc

import (
	"testing"

	"github.com/golang/protobuf/proto"
	. "github.com/smartystreets/goconvey/convey"

	assocv1 "github.com/sylvan/auro/apis/assoc/v1"
	commonv1 "github.com/sylvan/auro/apis/common/v1"
	assocclient "github.com/sylvan/auro/pkg/client/assoc"
	"github.com/sylvan/auro/pkg/service/assoc/domain"
)

func TestCreateInvalidAssoc(t *testing.T) {
	Convey("Testing create assoc with invalid request", t, FailureHalts, func(c C) {

		testCases := []struct {
			request *assocv1.CreateAssocRequest
			errMsg  string
		}{
			{request: &assocv1.CreateAssocRequest{}, errMsg: "field assoc.from_id invalid value: 0"},
			{request: &assocv1.CreateAssocRequest{Assoc: &assocv1.Assoc{}}, errMsg: "field assoc.from_id invalid value: 0"},
			{request: &assocv1.CreateAssocRequest{Assoc: &assocv1.Assoc{FromId: 1}}, errMsg: "field assoc.a_type invalid value: ASSOC_TYPE_UNSPECIFIED"},
			{request: &assocv1.CreateAssocRequest{Assoc: &assocv1.Assoc{FromId: 1, AType: assocv1.AssocType_ASSOC_TYPE_CREATED_BY}}, errMsg: "field assoc.to_id invalid value: 0"},
		}
		for i, tc := range testCases {
			resp, err := assocclient.GetInstance(rootCtx).CreateAssoc(rootCtx, tc.request)
			c.So(err, ShouldBeNil)
			_, _ = c.Printf("case #%d: request=%+v errMsg=%+v acturalMsg=%+v\n", i, tc.request, tc.errMsg, resp.Message)
			c.So(resp.Message, ShouldEqual, tc.errMsg)
		}
	})
}

func TestCRUDAssoc(t *testing.T) {
	Convey("Testing CRUD opeartion from AssocService!", t, func(c C) {
		//test create api
		GetInstance().DeleteAssoc(
			rootCtx,
			&assocv1.DeleteAssocRequest{
				FromId: 54, ToId: 55,
				AType: assocv1.AssocType_ASSOC_TYPE_CREATED_BY})
		assocEo := assocv1.Assoc{
			FromId: 54,
			ToId:   55,
			AType:  assocv1.AssocType_ASSOC_TYPE_CREATED_BY,
		}
		resp, err := GetInstance().CreateAssoc(rootCtx, &assocv1.CreateAssocRequest{Assoc: &assocEo})
		c.So(err, ShouldBeNil)
		c.So(resp.Details.Id, ShouldBeGreaterThan, 0)

		//test fault
		getFaultResp, err := assocclient.GetInstance(rootCtx).GetAssoc(rootCtx, &assocv1.GetAssocRequest{FromId: resp.Details.FromId + 1, ToId: resp.Details.ToId, AType: resp.Details.AType})
		c.So(err, ShouldBeNil)
		c.So(getFaultResp.Code, ShouldEqual, 2001)
		c.So(getFaultResp.Message, ShouldEqual, "数据库查询错误")

		//test right
		getRightResp, err := GetInstance().GetAssoc(rootCtx, &assocv1.GetAssocRequest{FromId: resp.Details.FromId, ToId: resp.Details.ToId, AType: resp.Details.AType})
		c.So(err, ShouldBeNil)
		c.So(getRightResp.Details, ShouldNotBeNil)

		//test revert
		atype := domain.GetRevertType(resp.Details.AType)
		getRevertResp, err := GetInstance().GetAssoc(rootCtx, &assocv1.GetAssocRequest{FromId: resp.Details.ToId, ToId: resp.Details.FromId, AType: atype})
		c.So(err, ShouldBeNil)
		c.So(getRevertResp.Details, ShouldNotBeNil)

		//test update api
		originAtype := resp.Details.AType
		resp.Details.AType = assocv1.AssocType_ASSOC_TYPE_TAGTO
		updateResp, err := GetInstance().UpdateAssoc(rootCtx, &assocv1.UpdateAssocRequest{FromId: resp.Details.FromId, ToId: resp.Details.ToId, AType: originAtype, Assoc: resp.Details})
		c.So(err, ShouldBeNil)
		c.So(assocv1.AssocType_ASSOC_TYPE_TAGTO, ShouldEqual, updateResp.Details.AType)
		getUpdateResp, err := GetInstance().GetAssoc(rootCtx, &assocv1.GetAssocRequest{FromId: resp.Details.FromId, ToId: resp.Details.ToId, AType: assocv1.AssocType_ASSOC_TYPE_TAGTO})
		c.So(err, ShouldBeNil)
		c.So(getUpdateResp.Details.AType, ShouldEqual, assocv1.AssocType_ASSOC_TYPE_TAGTO)

		//test delete api
		deleteResp, err := GetInstance().DeleteAssoc(rootCtx, &assocv1.DeleteAssocRequest{FromId: resp.Details.FromId, ToId: resp.Details.ToId, AType: updateResp.Details.AType})
		c.So(err, ShouldBeNil)
		c.So(proto.Equal(deleteResp.Details, updateResp.Details), ShouldBeTrue)

		getDeleteResp, err := assocclient.GetInstance(rootCtx).GetAssoc(rootCtx, &assocv1.GetAssocRequest{FromId: resp.Details.FromId, ToId: resp.Details.ToId, AType: updateResp.Details.AType})
		c.So(err, ShouldBeNil)
		c.So(getDeleteResp.Code, ShouldEqual, 2001)
		c.So(getFaultResp.Message, ShouldEqual, "数据库查询错误")

		countResp, err := GetInstance().CountAssocs(rootCtx, &assocv1.CountAssocsRequest{FromId: assocEo.FromId, AType: assocEo.AType})
		c.So(err, ShouldBeNil)
		c.So(countResp.Details, ShouldNotBeNil)
	})

}

func TestDeleteAssocsWithEmptyFilter(t *testing.T) {
	Convey("Testing delete assoc with empty filter!", t, func(c C) {
		testCases := []*assocv1.DeleteAssocsRequest{
			{},
			{CommonFilter: &commonv1.CommonFilter{}},
			{CommonFilter: &commonv1.CommonFilter{}, OptionFilter: &assocv1.AssocOptionFilter{}},
		}
		for i, tc := range testCases {
			_, _ = c.Printf("case #%d: filter=%+v\n", i, tc.GetCommonFilter())
			deleteResp, err := GetInstance().DeleteAssocs(rootCtx, tc)
			c.So(err, ShouldBeNil)
			c.So(deleteResp.Details, ShouldBeEmpty)
		}
	})
}

func TestDeleteAssocsWithFilter(t *testing.T) {
	Convey("Testing delete assoc with filter!", t, func(c C) {
		for i := 1; i < 11; i++ {
			assocEo := &assocv1.Assoc{
				FromId: int64(i),
				ToId:   101,
				AType:  assocv1.AssocType_ASSOC_TYPE_CREATED_BY,
			}
			resp, err := GetInstance().CreateAssoc(rootCtx, &assocv1.CreateAssocRequest{Assoc: assocEo})
			c.So(err, ShouldBeNil)
			c.So(resp.Details.Id, ShouldBeGreaterThan, 0)
		}
		deleteResp, err := GetInstance().DeleteAssocs(rootCtx, &assocv1.DeleteAssocsRequest{OptionFilter: &assocv1.AssocOptionFilter{FromId: 101, AType: domain.GetRevertType(assocv1.AssocType_ASSOC_TYPE_CREATED_BY)}})
		c.So(err, ShouldBeNil)
		c.So(deleteResp.Details, ShouldNotBeEmpty)
	})
}
