from __future__ import division

import os
import re
import shutil
import time

import handleScel
import wx

Index = 0


def GetSeparattion(sent):
    #断字
    result = []
    w = 0
    flag = True
    while w < len(sent):
        if len(result) == 2:
            return result
        if len(str([sent[w]])) == 8:
            flag = True
            result.append(w)
            w += 2
        else:
            if flag:
                result.append(w)
            flag = False
            w += 1
    result.append(len(sent))
    return result


def GetSeparattion1(sent):
    #断字
    result = []
    w = 0
    flag = True
    while w < len(sent):
        if len(str([sent[w]])) == 8:
            flag = True
            result.append(w)
            w += 2
        else:
            if flag:
                result.append(w)
            flag = False
            w += 1
    result.append(len(sent))
    return result


def FindWordTag(w):
    dicPath = os.path.abspath('.') + '\\wordsTagDic\\'
    if not os.path.isdir(dicPath):
        return None
    wi = GetSeparattion(w)
    wn = w[wi[0]:wi[1]]
    pattern = re.compile(r'\s+' + w + r':\S+')
    wordList = []
    try:
        f = open(dicPath + wn + '.txt', 'r')
        wordList = pattern.findall(f.read())
        f.close()
    except:
        None
    if not wordList:
        return None
    if len(wordList) == 1:
        tList = wordList[0].split(':')[1].split(',')
        if len(tList) == 1:
            return tList[0]
        else:
            return tList
    return None


def CreatDic(sent):
    #获取标注集
    absPath = os.path.abspath('.')
    dicPath = absPath + '\\wordsTagDic\\'
    if not os.path.isdir(dicPath):
        os.mkdir(dicPath)
    pattern = re.compile(r'\S+')
    wordList = pattern.findall(sent)
    for w in wordList:
        if not w:
            continue
        wi = w.split('/')
        if not wi[0]:
            continue
        sepPos = GetSeparattion(wi[0])
        word = wi[0][sepPos[0]:sepPos[1]]
        filePath = dicPath + word + '.txt'
        if os.path.isfile(filePath):
            tagDic = {}
            f = open(filePath, 'r')
            text = f.read().split('\n')
            f.close()
            for s in text:
                if s:
                    si = s.split(':')
                    if si[0]:
                        tagDic[si[0]] = [t for t in si[1].split(',') if t]
            if ' ' + wi[0] in tagDic.keys():
                if wi[1] not in tagDic[' ' + wi[0]]:
                    tagDic[' ' + wi[0]].append(wi[1])
            else:
                tagDic[' ' + wi[0]] = [wi[1]]
            f = open(filePath, 'w')
            f.write('\n'.join([
                k + ':' + ','.join(tagDic[k]) for k in sorted(tagDic.keys())
            ]))
            f.close()
        else:
            f = open(filePath, 'w')
            f.write(' ' + wi[0] + ':' + wi[1])
            f.close()
            None


def ONRecognition2(sent, tList=['nt', 'ns', 'nr', 'nz']):
    pattern = re.compile(r'\S+(?=/)')
    pattern2 = re.compile(r'\S+(?=:)')
    pattern3 = re.compile(r'(?<=:)\S+')
    bestP = 0
    bestSent = sent
    for t in tList:
        text = ''
        try:
            f = open(os.path.abspath('.') + '\\ONDic\\' + t + '.txt', 'r')
            text = f.read()
            f.close()
        except:
            continue
        wDic = {}
        L1 = pattern2.findall(text)
        L2 = pattern3.findall(text)
        N2 = 0
        for wn in range(len(L1)):
            N2 += int(L2[wn])
            if L1[wn] in wDic.keys():
                wDic[L1[wn]] += int(L2[wn])
            else:
                wDic[L1[wn]] = int(L2[wn])
        V = len(wDic)
        if (N2 + V) <= 0:
            continue
        newSent = ''
        P = 0
        for w in pattern.findall(sent):
            wL = GetSeparattion1(w)
            p = 0
            for i in range(len(wL) - 1):
                wi = w[wL[i]:wL[i + 1]]
                N1 = 0
                try:
                    N1 = wDic[wi]
                except:
                    None
                p += (1 + N1) / (V + N2)
            newSent += w + '/' + t + ' '
            P += p
        if P >= bestP:
            print(bestSent, '->', newSent, ',', bestP, '->', P)
            bestSent = newSent
            bestP = P
    return [bestSent]


def ONRecognition3(sent, tList=['nt', 'nr', 'ns', 'nz']):
    pattern = re.compile(r'\S+(?=/)')
    pattern2 = re.compile(r'(?<=/)\S+(?=/)')
    pattern3 = re.compile(r'(?<=:)\S+')
    bestP = 0
    bestSent = sent
    for t in tList:
        newSent = ''
        P = 0
        for w in pattern.findall(sent):
            wL = GetSeparattion1(w)
            p = 0
            for i in range(len(wL) - 1):
                wi = w[wL[i]:wL[i + 1]]
                text = ''
                try:
                    f = open(
                        os.path.abspath('.') + '\\RoleDicFre\\' + wi + '.txt',
                        'r')
                    text = f.read()
                    f.close()
                except:
                    None
                wDic = {}
                L1 = pattern2.findall(text)
                L2 = pattern3.findall(text)
                N2 = 0
                for wn in range(len(L1)):
                    N2 += int(L2[wn])
                    if L1[wn] in wDic.keys():
                        wDic[L1[wn]] += int(L2[wn])
                    else:
                        wDic[L1[wn]] = int(L2[wn])
                N1 = 0
                try:
                    N1 = wDic[t]
                except:
                    None
                V = len(wDic)
                if (V + N2) > 0:
                    p = p + (1 + N1) / (V + N2)
            if p / len(w) > 0:
                newSent += w + '/' + t + str(p / len(w)) + '  '
            else:
                newSent += w + '/' + t + str(p / len(w)) + '  '
            P += p
        if P > bestP:
            bestP = P
            bestSent = newSent
    return [bestSent]


def ONRecognition(sent, tList=['nt', 'nr', 'ns', 'nz']):
    pattern = re.compile(r'\S+(?=/)')

    bestP = 0
    bestSent = ''
    for w in pattern.findall(sent):
        tw = FindWordTag(w)
        if tw:
            if type(tw) == str:
                bestSent += w + '/' + tw + ' '
                continue
            else:
                tList = tw
        wL = GetSeparattion1(w)
        LDic = {}  #位置字典,标注为B,S,E,M
        if len(wL) == 2:
            LDic[(wL[0], wL[1])] = 'S'
        elif len(wL) == 3:
            LDic[(wL[0], wL[1])] = 'B'
            LDic[(wL[1], wL[2])] = 'E'
        elif len(wL) > 3:
            LDic[(wL[0], wL[1])] = 'B'
            for i in range(1, len(wL) - 2):
                LDic[(wL[i], wL[i + 1])] = 'M'
            LDic[(wL[-2], wL[-1])] = 'E'
        tPDic = {}
        for t in tList:
            tPDic[t] = 1
        for k in sorted(LDic.keys()):
            wi = w[k[0]:k[1]]  #字
            L = LDic[k]  #位置
            text = ''
            try:
                f = open(
                    os.path.abspath('.') + '\\RoleDicFre\\' + wi + '.txt', 'r')
                text = f.read()
                f.close()
            except:
                continue
            tDic = {}  #标注字典,标注+概率
            pattern2 = re.compile(r'(?<=/)\S+(?=/' + L + r')')
            pattern3 = re.compile(r'(?<=' + L + r':)\S+')
            L1 = pattern2.findall(text)
            L2 = pattern3.findall(text)
            N2 = 0
            for wn in range(len(L1)):
                N2 += int(L2[wn])

                if L1[wn] in tDic.keys():
                    tDic[L1[wn]] += int(L2[wn])
                else:
                    tDic[L1[wn]] = int(L2[wn])
            V = len(tDic)
            if (V + N2) > 0:
                for t in tList:
                    N1 = 0
                    try:
                        N1 = tDic[t]
                    except:
                        None
                    if t in tPDic.keys():
                        tPDic[t] = tPDic[t] * (1 + N1) / (V + N2)
                    else:
                        tPDic[t] = (1 + N1) / (V + N2)

        bestP = 0
        bestt = ''
        for t in tPDic.keys():
            if tPDic[t] >= bestP:
                bestt = t
                bestP = tPDic[t]
        bestSent += w + '/' + bestt + ' '
    bestSent = bestSent[:-1]
    return [bestSent]


def CreatONDic(sent):
    #创建名词标注字典,每个标注用一个文本记录,里面记录某个字在名词中出现的次数
    dicPath = os.path.abspath('.') + '\\ONDic\\'
    pattern2 = re.compile(r'\S+(?=:)')
    pattern3 = re.compile(r'(?<=:)\S+')
    if not os.path.isdir(dicPath):
        os.mkdir(dicPath)
    tList = sorted(set(re.compile(r'(?<=/)\S+').findall(sent)))
    for t in tList:
        filePath = dicPath + t + '.txt'
        tDic = {}
        if os.path.isfile(filePath):
            f = open(filePath, 'r')
            text = f.read()
            f.close()
            L1 = pattern2.findall(text)
            L2 = pattern3.findall(text)
            for i in range(len(L1)):
                tDic[L1[i]] = int(L2[i])
        pattern = re.compile(r'\S+(?=/' + t + r')')
        for w in pattern.findall(sent):
            wL = GetSeparattion1(w)
            for i in range(len(wL) - 1):
                word = w[wL[i]:wL[i + 1]]
                if word in tDic.keys():
                    tDic[word] += 1
                else:
                    tDic[word] = 1
        f = open(filePath, 'w')
        f.write('\n'.join(
            [k + ':' + str(tDic[k]) for k in sorted(tDic.keys())]))
        f.close()


def MorphemeRoleDic(sent):
    #语素角色词典
    f = open(os.path.abspath('.') + '\\RoleDic.txt', 'r')
    text = f.read()
    f.close()
    pattern1 = re.compile(r'\S+(?=:)')
    pattern2 = re.compile(r'(?<=:)\S+')
    pattern3 = re.compile(r'\S+(?=/)')
    pattern4 = re.compile(r'(?<=/)\S+')
    L1 = pattern1.findall(text)
    L2 = pattern2.findall(text)
    L3 = pattern3.findall(sent)
    L4 = pattern4.findall(sent)
    RoleDic = {}
    wordDic = {}
    for i in range(len(L1)):
        RoleDic[L1[i]] = [t for t in L2[i].split(',')]
    for i in range(len(L3)):
        L3Sep = GetSeparattion1(L3[i])
        wDic = {}
        if len(L3Sep) == 2:
            wDic[(L3Sep[0], L3Sep[1])] = L4[i] + '/S'
        elif len(L3Sep) == 3:
            wDic[(L3Sep[0], L3Sep[1])] = L4[i] + '/B'
            wDic[(L3Sep[1], L3Sep[2])] = L4[i] + '/E'
        elif len(L3Sep) > 3:
            wDic[(L3Sep[0], L3Sep[1])] = L4[i] + '/B'
            for j in range(1, len(L3Sep) - 2):
                wDic[(L3Sep[j], L3Sep[j + 1])] = L4[i] + '/M'
            wDic[(L3Sep[-2], L3Sep[-1])] = L4[i] + '/E'
        for k in wDic.keys():
            w = L3[i][k[0]:k[1]]
            t = wDic[k]
            if w in RoleDic.keys():
                if t not in RoleDic[w]:
                    RoleDic[w].append(t)
            else:
                RoleDic[w] = [t]
            t = '/' + t
            if w in wordDic.keys():
                if t in wordDic[w].keys():
                    wordDic[w][t] += 1
                else:
                    wordDic[w][t] = 1
            else:
                wordDic[w] = {}
                wordDic[w][t] = 1
    f = open(os.path.abspath('.') + '\\RoleDic.txt', 'w')
    f.write('\n'.join(
        [k + ':' + ','.join(RoleDic[k]) for k in sorted(RoleDic.keys())]))
    f.close()
    dicPath = os.path.abspath('.') + '\\RoleDicFre\\'
    if not os.path.isdir(dicPath):
        os.mkdir(dicPath)
    for k in wordDic.keys():
        filePath = dicPath + k + '.txt'
        if not os.path.isfile(filePath):
            f = open(filePath, 'w')
            f.write('\n'.join(
                [ki + ':' + str(wordDic[k][ki]) for ki in wordDic[k].keys()]))
            f.close()
            None
        else:
            f = open(filePath, 'r')
            text = f.read()
            f.close()
            L1 = pattern1.findall(text)
            L2 = pattern2.findall(text)
            for i in range(len(L1)):
                if L1[i] in wordDic[k].keys():
                    wordDic[k][L1[i]] += int(L2[i])
                else:
                    wordDic[k][L1[i]] = int(L2[i])
            f = open(filePath, 'w')
            f.write('\n'.join(
                [ki + ':' + str(wordDic[k][ki]) for ki in wordDic[k].keys()]))
            f.close()
        wordDic.pop(k)


class handleScelFrame(wx.Frame):

    def __init__(self):
        global Index
        wx.Frame.__init__(self, None, -1, u'handleScel测试版01', size=(500, 500))
        self.dicPath = os.path.abspath('.') + '\\special\\'
        self.dicPath2 = os.path.abspath('.') + '\\special2\\'
        if not os.path.isdir(self.dicPath2):
            os.mkdir(self.dicPath2)
        self.sentList = sorted(
            [f for f in os.listdir(os.path.abspath('.') + '\\special\\') if f])
        self.Index = int(
            re.compile('\S+').findall(
                open(os.path.abspath('.') + '\\handleIndex.txt',
                     'r').read())[0]) % len(self.sentList)
        self.panel = wx.Panel(self, -1)
        self.sizer = wx.FlexGridSizer(cols=2, hgap=10, vgap=10)
        label1 = wx.StaticText(self.panel, -1, u'待处理文本')
        self.text1 = wx.TextCtrl(self.panel,
                                 -1,
                                 self.sentList[0],
                                 size=(350, 20))
        label2 = wx.StaticText(self.panel, -1, u'已处理文本')
        self.text2 = wx.TextCtrl(self.panel,
                                 -1,
                                 '',
                                 size=(350, 300),
                                 style=wx.TE_MULTILINE)
        self.button1 = wx.Button(self.panel, -1, u'开始处理')
        self.button2 = wx.Button(self.panel, -1, u'加入词典')
        self.button3 = wx.Button(self.panel, -1, u'下一条')
        self.button4 = wx.Button(self.panel, -1, u'上一条')
        self.nr = wx.Button(self.panel, -1, u'nr人名')  #人名
        self.nt = wx.Button(self.panel, -1, u'nt机构名')  #机构名
        self.ns = wx.Button(self.panel, -1, u'ns地名')  #地名
        self.nz = wx.Button(self.panel, -1, u'nz其它专名')  #其它专名
        self.sizer.AddMany([
            label1, self.text1, label2, self.text2, self.button1, self.button2,
            self.button4, self.button3, self.nr, self.nt, self.ns, self.nz
        ])
        self.panel.SetSizer(self.sizer)
        self.Bind(wx.EVT_BUTTON, self.OnButton1, self.button1)
        self.Bind(wx.EVT_BUTTON, self.OnButton2, self.button2)
        self.Bind(wx.EVT_BUTTON, self.OnButton3, self.button3)
        self.Bind(wx.EVT_BUTTON, self.OnButton4, self.button4)
        self.Bind(wx.EVT_BUTTON, self.OnNr, self.nr)
        self.Bind(wx.EVT_BUTTON, self.OnNt, self.nt)
        self.Bind(wx.EVT_BUTTON, self.OnNs, self.ns)
        self.Bind(wx.EVT_BUTTON, self.OnNz, self.nz)

    def OnButton1(self, event):
        fileName = self.dicPath + self.sentList[self.Index]
        self.text1.SetValue(self.sentList[self.Index])
        handleScel.deal(fileName)
        self.text2.SetValue('\n'.join(
            sorted(set([f[2] + '/' for f in handleScel.GTable]))))

    def OnButton2(self, event):
        global Index
        text = self.text2.GetValue()
        if re.compile(r'(?<=/)\S+').findall(text):
            try:
                fileName = self.text1.GetValue().encode('gb2312')
            except:
                self.text2.SetValue('Error')
                return
            f = open(self.dicPath2 + fileName + '.txt', 'w')
            f.write('')
            f.close()
            f = open(self.dicPath2 + fileName + '.txt', 'a')
            resultList = text.split('\n')
            for s in resultList:
                flag = True
                try:
                    s = s.encode('gb2312')
                except:
                    flag = False
                if s and flag:
                    f.write(s + '\n')
            self.text2.SetValue('Done')
            f.close()
            Index = self.Index + 1
            self.Index += 1
            self.Index = self.Index % len(self.sentList)
            try:
                self.text1.SetValue(self.sentList[self.Index])
                self.text2.SetValue('Done2')
            except:
                self.text2.SetValue('False')
            handleScel.GPy_Table = {}
            handleScel.GTable = []
            fileName = self.dicPath + self.sentList[self.Index]
            self.text1.SetValue(self.sentList[self.Index])
            handleScel.deal(fileName)
            self.text2.SetValue('\n'.join(
                sorted(set([f[2] + '/' for f in handleScel.GTable]))))
        else:
            self.text2.SetValue('Please adding a tag')

    def OnButton3(self, event):
        handleScel.GPy_Table = {}
        handleScel.GTable = []
        self.Index += 1
        while not self.sentList[self.Index]:
            self.Index += 1
            self.Index = self.Index % len(self.sentList)
        try:
            self.text1.SetValue(self.sentList[self.Index])
        except:
            print(False)

    def OnButton4(self, event):
        handleScel.GPy_Table = {}
        handleScel.GTable = []
        self.Index -= 1
        while not self.sentList[self.Index]:
            self.Index -= 1
            self.Index = self.Index % len(self.sentList)
        try:
            self.text1.SetValue(self.sentList[self.Index])
        except:
            print(False)

    def OnNr(self, event):
        wordList = [
            w + '/nr'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))

    def OnNt(self, event):
        wordList = [
            w + '/nt'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))

    def OnNs(self, event):
        wordList = [
            w + '/ns'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))

    def OnNz(self, event):
        wordList = [
            w + '/nz'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))


class handleScelFrame2(wx.MDIParentFrame):

    def __init__(self):
        global Index
        wx.MDIParentFrame.__init__(self,
                                   None,
                                   -1,
                                   u'handleScel测试版02',
                                   size=(500, 500),
                                   style=wx.DEFAULT_FRAME_STYLE | wx.VSCROLL)

        self.dicPath = os.path.abspath('.') + '\\special2\\'
        self.dicPath2 = os.path.abspath('.') + '\\special3\\'
        if not os.path.isdir(self.dicPath2):
            os.mkdir(self.dicPath2)
        self.sentList = sorted([f for f in os.listdir(self.dicPath) if f])
        self.Index = 0  #int(re.compile('\S+').findall(open(os.path.abspath('.')+'\\handleIndex.txt','r').read())[0])%len(self.sentList)
        self.panel = wx.Panel(self, -1)
        self.sizer = wx.FlexGridSizer(cols=2, hgap=10, vgap=10)
        label1 = wx.StaticText(self.panel, -1, u'待处理文本')
        self.text1 = wx.TextCtrl(self.panel,
                                 -1,
                                 self.sentList[0],
                                 size=(350, 20))
        label2 = wx.StaticText(self.panel, -1, u'已处理文本')
        self.text2 = wx.TextCtrl(self.panel,
                                 -1,
                                 '',
                                 size=(350, 300),
                                 style=wx.TE_MULTILINE)
        self.button1 = wx.Button(self.panel, -1, u'开始处理')
        self.button2 = wx.Button(self.panel, -1, u'加入词典')
        self.button3 = wx.Button(self.panel, -1, u'下一条')
        self.button4 = wx.Button(self.panel, -1, u'上一条')
        self.nr = wx.Button(self.panel, -1, u'nr人名')  #人名
        self.nt = wx.Button(self.panel, -1, u'nt机构名')  #机构名
        self.ns = wx.Button(self.panel, -1, u'ns地名')  #地名
        self.nz = wx.Button(self.panel, -1, u'nz其它专名')  #其它专名
        self.sizer.AddMany([
            label1, self.text1, label2, self.text2, self.button1, self.button2,
            self.button4, self.button3, self.nr, self.nt, self.ns, self.nz
        ])
        self.panel.SetSizer(self.sizer)
        self.Bind(wx.EVT_BUTTON, self.OnButton1, self.button1)
        self.Bind(wx.EVT_BUTTON, self.OnButton2, self.button2)
        self.Bind(wx.EVT_BUTTON, self.OnButton3, self.button3)
        self.Bind(wx.EVT_BUTTON, self.OnButton4, self.button4)
        self.Bind(wx.EVT_BUTTON, self.OnNr, self.nr)
        self.Bind(wx.EVT_BUTTON, self.OnNt, self.nt)
        self.Bind(wx.EVT_BUTTON, self.OnNs, self.ns)
        self.Bind(wx.EVT_BUTTON, self.OnNz, self.nz)

    def OnButton1(self, event):
        fileName = self.dicPath + self.sentList[self.Index]
        self.text1.SetValue(self.sentList[self.Index])
        handleScel.deal(fileName)
        self.text2.SetValue('\n'.join(
            sorted(set([f[2] + '/' for f in handleScel.GTable]))))

    def OnButton2(self, event):
        global Index
        text = self.text2.GetValue()
        if re.compile(r'(?<=/)\S+').findall(text):
            try:
                fileName = self.text1.GetValue().encode('gb2312')
            except:
                self.text2.SetValue('Error')
                return
            f = open(self.dicPath2 + fileName + '.txt', 'w')
            f.write('')
            f.close()
            f = open(self.dicPath2 + fileName + '.txt', 'a')
            print(fileName)
            resultList = text.split('\n')
            for s in resultList:
                flag = True
                try:
                    s = s.encode('gb2312')
                except:
                    flag = False
                if s and flag:
                    f.write(s + '\n')
            self.text2.SetValue('Done')
            f.close()
            Index = self.Index + 1
            self.Index += 1
            self.Index = self.Index % len(self.sentList)
            try:
                self.text1.SetValue(self.sentList[self.Index])
                self.text2.SetValue('Done2')
            except:
                self.text2.SetValue('False')
            handleScel.GPy_Table = {}
            handleScel.GTable = []
            fileName = self.dicPath + self.sentList[self.Index]
            self.text1.SetValue(self.sentList[self.Index])
            handleScel.deal(fileName)
            self.text2.SetValue('\n'.join(
                sorted(set([f[2] + '/' for f in handleScel.GTable]))))
        else:
            self.text2.SetValue('Please adding a tag')

    def OnButton3(self, event):
        handleScel.GPy_Table = {}
        handleScel.GTable = []
        self.Index += 1
        while not self.sentList[self.Index]:
            self.Index += 1
            self.Index = self.Index % len(self.sentList)
        try:
            self.text1.SetValue(self.sentList[self.Index])
        except:
            print(False)

    def OnButton4(self, event):
        handleScel.GPy_Table = {}
        handleScel.GTable = []
        self.Index -= 1
        while not self.sentList[self.Index]:
            self.Index -= 1
            self.Index = self.Index % len(self.sentList)
        try:
            self.text1.SetValue(self.sentList[self.Index])
        except:
            print(False)

    def OnNr(self, event):
        wordList = [
            w + '/nr'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))

    def OnNt(self, event):
        wordList = [
            w + '/nt'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))

    def OnNs(self, event):
        wordList = [
            w + '/ns'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))

    def OnNz(self, event):
        wordList = [
            w + '/nz'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))


class handleScelFrame3(wx.Frame):

    def __init__(self):
        global Index
        wx.Frame.__init__(self, None, -1, u'handleScel测试版03', size=(500, 500))
        self.dicPath = os.path.abspath('.') + u'\\城市信息精选部分\\'.encode('gb2312')
        self.dicPath2 = os.path.abspath('.') + u'\\城市信息精选部分标注结果\\'.encode(
            'gb2312')
        self.dicPath3 = os.path.abspath('.') + u'\\城市信息精选部分完结\\'.encode(
            'gb2312')
        if not os.path.isdir(self.dicPath2):
            os.mkdir(self.dicPath2)
        if not os.path.isdir(self.dicPath3):
            os.mkdir(self.dicPath3)
        self.sentList = ''
        try:
            self.sentList = sorted([f for f in os.listdir(self.dicPath) if f])
            print(self.sentList)
        except:
            None
        if not self.sentList:
            self.panel = wx.Panel(self, -1)
            self.text = wx.StaticText(
                self.panel, -1,
                u'The file path do not exist or do not has any subpath')
            return
        self.Index = 0  #int(re.compile('\S+').findall(open(os.path.abspath('.')+'\\handleIndex.txt','r').read())[0])%len(self.sentList)
        self.panel = wx.Panel(self, -1)
        self.sizer = wx.FlexGridSizer(cols=2, hgap=10, vgap=10)
        label1 = wx.StaticText(self.panel, -1, u'待处理文本')
        self.text1 = wx.TextCtrl(self.panel,
                                 -1,
                                 self.sentList[0],
                                 size=(350, 20))
        label2 = wx.StaticText(self.panel, -1, u'已处理文本')
        self.text2 = wx.TextCtrl(self.panel,
                                 -1,
                                 '',
                                 size=(350, 300),
                                 style=wx.TE_MULTILINE)
        self.button1 = wx.Button(self.panel, -1, u'开始处理')
        self.button2 = wx.Button(self.panel, -1, u'加入词典')
        self.button3 = wx.Button(self.panel, -1, u'下一条')
        self.button4 = wx.Button(self.panel, -1, u'上一条')
        self.nr = wx.Button(self.panel, -1, u'nr人名')  #人名
        self.nt = wx.Button(self.panel, -1, u'nt机构名')  #机构名
        self.ns = wx.Button(self.panel, -1, u'ns地名')  #地名
        self.nz = wx.Button(self.panel, -1, u'nz其它专名')  #其它专名
        self.sizer.AddMany([
            label1, self.text1, label2, self.text2, self.button1, self.button2,
            self.button4, self.button3, self.nr, self.nt, self.ns, self.nz
        ])
        self.panel.SetSizer(self.sizer)
        self.Bind(wx.EVT_BUTTON, self.OnButton1, self.button1)
        self.Bind(wx.EVT_BUTTON, self.OnButton2, self.button2)
        self.Bind(wx.EVT_BUTTON, self.OnButton3, self.button3)
        self.Bind(wx.EVT_BUTTON, self.OnButton4, self.button4)
        self.Bind(wx.EVT_BUTTON, self.OnNr, self.nr)
        self.Bind(wx.EVT_BUTTON, self.OnNt, self.nt)
        self.Bind(wx.EVT_BUTTON, self.OnNs, self.ns)
        self.Bind(wx.EVT_BUTTON, self.OnNz, self.nz)

    def OnButton1(self, event):
        fileName = self.dicPath + self.sentList[self.Index]
        self.text1.SetValue(self.sentList[self.Index])
        try:

            f = open(fileName, 'r')

            sentList = re.compile(r'\S+').findall(f.read())
            f.close()
            self.text2.SetValue('\n'.join(
                [ONRecognition(s)[0] for s in sentList]))
        except:
            self.text2.SetValue('No such a file name:' + fileName)

    def OnButton2(self, event):
        global Index
        text = self.text2.GetValue()
        if re.compile(r'(?<=/)\S+').findall(text):
            fileName = ''
            try:
                fileName = self.text1.GetValue().encode('gb2312')
            except:
                self.text2.SetValue('Error')
                return
            f = open(self.dicPath2 + fileName + '.txt', 'w')
            f.write('')
            f.close()
            f = open(self.dicPath2 + fileName + '.txt', 'a')
            print(fileName)
            resultList = text.split('\n')
            for s in resultList:
                flag = True
                try:
                    s = s.encode('gb2312')
                except:
                    flag = False
                if s and flag:
                    f.write(s + '\n')
            self.text2.SetValue('Done')
            f.close()
            try:
                shutil.copy(self.dicPath + fileName, self.dicPath3 + fileName)
                os.remove(self.dicPath + fileName)
            except:
                None
            Index = self.Index + 1
            self.Index += 1
            self.Index = self.Index % len(self.sentList)
            try:
                self.text1.SetValue(self.sentList[self.Index])
                self.text2.SetValue('Done2')
            except:
                self.text2.SetValue('False')
            fileName = self.dicPath + self.sentList[self.Index]
            self.text1.SetValue(self.sentList[self.Index])
            try:
                f = open(fileName, 'r')
                sentList = re.compile(r'\S+').findall(f.read())
                f.close()
                self.text2.SetValue('\n'.join(
                    [ONRecognition(s)[0] for s in sentList]))
            except:
                self.text2.SetValue('No such a file name:' + fileName)
        else:
            self.text2.SetValue('Please adding a tag')

    def OnButton3(self, event):
        handleScel.GPy_Table = {}
        handleScel.GTable = []
        self.Index += 1
        while not self.sentList[self.Index]:
            self.Index += 1
            self.Index = self.Index % len(self.sentList)
        try:
            self.text1.SetValue(self.sentList[self.Index])
        except:
            print(False)

    def OnButton4(self, event):
        handleScel.GPy_Table = {}
        handleScel.GTable = []
        self.Index -= 1
        while not self.sentList[self.Index]:
            self.Index -= 1
            self.Index = self.Index % len(self.sentList)
        try:
            self.text1.SetValue(self.sentList[self.Index])
        except:
            print(False)

    def OnNr(self, event):
        wordList = [
            w + '/nr'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))

    def OnNt(self, event):
        wordList = [
            w + '/nt'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))

    def OnNs(self, event):
        wordList = [
            w + '/ns'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))

    def OnNz(self, event):
        wordList = [
            w + '/nz'
            for w in re.compile(r'\S+(?=/)').findall(self.text2.GetValue())
        ]
        self.text2.SetValue('\n'.join(wordList))


def BatchProcess():
    dicPath1 = os.path.abspath('.') + '\\special2\\'
    dicPath = os.path.abspath('.') + '\\special3\\'
    dicPath2 = os.path.abspath('.') + '\\special4\\'
    dicPath3 = os.path.abspath('.') + '\\special2.1\\'
    fileList1 = os.listdir(dicPath1)
    os.listdir(dicPath2)
    fileList3 = os.listdir(dicPath3)
    if not os.path.isdir(dicPath):
        return
    if not os.path.isdir(dicPath2):
        os.mkdir(dicPath2)
    while True:
        fileList = os.listdir(dicPath)
        if not fileList:
            break
        for f in fileList:
            if f in fileList1:
                if f not in fileList3:
                    shutil.copy(dicPath1 + f, dicPath3 + f)
                os.remove(dicPath1 + f)
            time1 = time.time()
            fi = open(dicPath + f, 'r')
            sentList = fi.read().split('\n')
            fi.close()
            for s in sentList:
                if s:
                    try:
                        CreatDic(s)
                        CreatONDic(s)
                        MorphemeRoleDic(s)
                    except:
                        print(False, s)
            shutil.copy(dicPath + f, dicPath2 + f)
            os.remove(dicPath + f)
            print(f, time.time() - time1)


def removeSameDir():
    dicPath1 = os.path.abspath('.') + '\\special2\\'
    dicPath2 = os.path.abspath('.') + '\\special3\\'
    dicPath3 = os.path.abspath('.') + '\\special2.1\\'
    if not os.path.isdir(dicPath1) or not os.path.isdir(dicPath2):
        return
    if not os.path.isdir(dicPath3):
        os.mkdir(dicPath3)
    fileList1 = os.listdir(dicPath1)
    fileList2 = os.listdir(dicPath2)
    fileList3 = os.listdir(dicPath3)
    for f in fileList1:
        if f in fileList2:
            if f not in fileList3:
                shutil.copy(dicPath1 + f, dicPath3 + f)
            os.remove(dicPath1 + f)


def GetSRTZ():
    filePath = os.path.abspath('.') + '\\199802.txt'
    f = open(filePath, 'r')
    text = f.read()
    f.close()
    for t in ['nr', 'nt', 'ns', 'nz']:
        pattern = re.compile(r'\S+(?=/' + t + r')')
        f = open(os.path.abspath('.') + '\\' + t + '.txt', 'w')
        f.write('\n'.join(sorted(set(pattern.findall(text)))))
        f.close()


def GetSRTZ2():
    filePath = os.path.abspath('.') + '\\199802.txt'
    f = open(filePath, 'r')
    text = f.read()
    f.close()
    for t in ['nr', 'nt', 'ns', 'nz']:
        pattern = re.compile(r'\S+(?=/' + t + r')')
        f = open(os.path.abspath('.') + '\\' + t + 'last.txt', 'w')
        f.write('\n'.join(
            sorted(
                set([
                    s[GetSeparattion1(s)[-2]:GetSeparattion1(s)[-1]]
                    for s in sorted(set(pattern.findall(text)))
                ]))))
        f.close()


def BatchTest(path1, path2):
    if not path1 or not path2:
        path1 = os.path.abspath('.') + u'\\城市信息精选部分完结\\'.encode('gb2312')
        path2 = os.path.abspath('.') + u'\\城市信息精选部分标注结果\\'.encode('gb2312')
    fileList1 = os.listdir(path1)
    fileList2 = os.listdir(path2)
    path3 = os.path.abspath('.') + u'\\城市信息精选部分测试结果.txt'.encode('gb2312')
    pattern1 = re.compile(r'\S+(?=/)')
    pattern2 = re.compile(r'(?<=/)\S+')
    SumFLen = 0  #统计文本数
    SumR = 0  #统计召回率
    SumT = 0  #统计准确率
    file2 = open(path3, 'w')
    file2.write('')
    file2.close()
    file3 = open(path3, 'a')
    for f in fileList2:
        #f=f
        if f[:-4] in fileList1:
            fi = open(path1 + f[:-4], 'r')
            text1 = fi.read()
            fi.close()
            #sentList1=pattern1.findall(text1)
            tList1 = pattern2.findall(text1)
            fi = open(path2 + f, 'r')
            text2 = fi.read()
            fi.close()
            #sentList2=pattern1.findall(text2)
            tList2 = pattern2.findall(text2)
            tLen1 = len(tList1)
            tLen2 = len(tList2)
            Len = tLen1
            if tLen2 < Len:
                Len = tLen2
            T = 0
            for i in range(Len):
                if tList1[i] == tList2[i]:
                    T += 1

            R = 0
            if tLen1:
                R = T / tLen1
                SumR += R
            if tLen2:
                T = T / tLen2
                SumT += T
            file3.write(f + ' r: ' + str(R) + ' t: ' + str(T) + '\n')
            SumFLen += 1
    if SumFLen:
        file3.write('\n\n\nSumR:' + str(SumR / SumFLen) + '   SumT:' +
                    str(SumT / SumFLen) + '\n')
    file3.close()


def BatchReProcess():
    #将字典中的标注为nt,nr,ns和nz的文件读出写入到对应的文本中,并将标注为多个名词标注的词
    #找出,写入勘错的文件
    #
    tList = ['nt', 'nr', 'ns', 'nz']
    absPath = os.path.abspath('.') + u'\\专有名词\\'.encode('gb2312')
    if not os.path.isdir(absPath):
        os.mkdir(absPath)
    wrongPath = os.path.abspath('.') + '\\ExplorationWrong.txt'
    f = open(wrongPath, 'w')
    f.write('')
    f.close()
    pattern1 = re.compile(r'\S+(?=:)')
    pattern2 = re.compile(r'(?<=:)\S+')
    sourcePath = os.path.abspath('.') + '\\wordsTagDic\\'
    if not os.path.isdir(sourcePath):
        return
    for f in os.listdir(sourcePath):
        fi = open(sourcePath + f, 'r')
        text = fi.read()
        fi.close()
        wordList = pattern1.findall(text)
        tagList = pattern2.findall(text)
        for i in range(len(tagList)):
            t = tagList[i].split(',')
            if len(t) == 1:
                if t[0] in tList:
                    print(wordList[i], tagList[i])
                    dicPath = absPath + t[0] + '\\'
                    if not os.path.isdir(dicPath):
                        os.mkdir(dicPath)
                        fi = open(dicPath + f, 'w')
                        fi.write(wordList[i])
                        fi.close()
                    else:
                        if os.path.isfile(dicPath + f):
                            wList = re.compile(r'\S+').findall(
                                open(dicPath + f, 'r').read())
                            wList.append(wordList[i])
                            fi = open(dicPath + f, 'w')
                            fi.write('\n'.join(sorted(set(wList))))
                            fi.close()
                        else:
                            fi = open(dicPath + f, 'w')
                            fi.write(wordList[i])
                            fi.close()
            elif len(t) > 1:
                flag = True
                for ti in t:
                    if ti not in tList:
                        flag = False
                        break
                if flag:
                    print(wordList[i], tagList[i])
                    fi = open(wrongPath, 'a')
                    fi.write(wordList[i] + ':' + tagList[i] + '\n')
                    fi.close()


def findGongjiao():
    print(True)
    #将所有带公交的词库从special2移动到special3文件夹
    dicPath1 = os.path.abspath('.') + '\\special2\\'
    dicPath2 = os.path.abspath('.') + '\\special3\\'
    if not os.path.isdir(dicPath1):
        return
    if not os.path.isdir(dicPath2):
        os.mkdir(dicPath2)
    tag = u'地名'.encode('gb2312')
    print(tag)
    for f in os.listdir(dicPath1):
        if tag in f:
            print(f)
            shutil.copy(dicPath1 + f, dicPath2 + f)
    return


def ReTagging():
    pattern = re.compile(r'\S+(?=:)')
    wrongPath = os.path.abspath('.') + '\\ExplorationWrong.txt'
    f = open(wrongPath, 'r')
    sentList = pattern.findall(f.read())
    f.close()
    wrongPath = os.path.abspath('.') + '\\ExplorationWrong1.txt'
    f = open(wrongPath, 'w')
    f.write('\n'.join([ONRecognition(s + '/')[0] for s in sentList if s]))
    f.close()
    #return ONRecognition(s)[0]


def MiniNum(List):
    if not List:
        return None
    mini = List[0]
    if len(List) > 1:
        for i in range(1, len(List)):
            if List[i] < mini:
                mini = List[i]
    return mini


def MiniEdiDis(w1, w2):
    sep1 = GetSeparattion1(w1)
    sep2 = GetSeparattion1(w2)
    DList = []
    for i in range(len(sep1)):
        DList.append([i])
        for j in range(1, len(sep2)):
            if i == 0:
                DList[-1].append(j)
            else:
                D1 = DList[i - 1][j] + 1
                D2 = DList[i][j - 1] + 1
                D3 = DList[i - 1][j - 1]
                if not w1[sep1[i - 1]:sep1[i]] == w2[sep2[j - 1]:sep2[j]]:
                    D3 += 2
                DList[-1].append(MiniNum([D1, D2, D3]))
    return DList[-1][-1]  #*(len(sep2)-1)


def MiniEdiTag(w):
    tag = FindWordTag(w)
    if tag:
        if type(tag) == str:
            return '/' + tag
        else:
            return '/' + ONRecognition(w + '/', tag)[0].split('/')[1]
    dicPath = os.path.abspath('.') + u'\\专有名词\\'.encode('gb2312')
    if not os.path.isdir(dicPath):
        return ''
    tagDic = {}
    pattern = re.compile(r'\S+')
    for f in os.listdir(dicPath):
        miniDis = len(w) * len(w)
        miniW = w
        for fi in os.listdir(dicPath + f + '\\'):
            fo = open(dicPath + f + '\\' + fi, 'r')
            wordList = pattern.findall(fo.read())
            fo.close()
            for wi in wordList:
                mini = MiniEdiDis(w, wi)
                if mini < miniDis:
                    miniW = wi
                    miniDis = mini
                elif mini == miniDis:
                    if abs(len(wi) - len(w)) < abs(len(miniW) - len(w)):
                        miniDis = mini
                        miniW = wi
        print(w, '--->', miniW, f, miniDis)
        if miniDis in tagDic.keys():
            if abs(len(miniW) -
                   len(w)) < abs(len(tagDic[miniDis].split(':')[1]) - len(w)):
                tagDic[miniDis] = '/' + f + ':' + miniW + ':' + str(miniDis)
            elif abs(len(miniW) - len(w)) == abs(
                    len(tagDic[miniDis].split(':')[1]) - len(w)):
                if ONRecognition(w + '/')[0].split('/')[1] == f:
                    tagDic[miniDis] = '/' + f + ':' + miniW + ':' + str(
                        miniDis)
        else:
            tagDic[miniDis] = '/' + f + ':' + miniW + ':' + str(miniDis)
    if not tagDic:
        return ''
    else:
        print(tagDic[sorted(tagDic.keys())[0]])
        return tagDic[sorted(tagDic.keys())[0]]


def ReTagging2():
    pattern = re.compile(r'\S+(?=:)')
    wrongPath = os.path.abspath('.') + '\\ExplorationWrong.txt'
    f = open(wrongPath, 'r')
    sentList = pattern.findall(f.read())
    f.close()
    wrongPath = os.path.abspath('.') + '\\ExplorationWrong2.txt'
    f = open(wrongPath, 'w')
    f.write('\n'.join([s + MiniEdiTag(s) for s in sentList if s]))
    f.close()


def RefreshN(w):
    ti = w.split('/')
    if not len(ti) == 2:
        return
    if ti[1] not in ['ns', 'nt', 'nr', 'nz']:
        return
    dicPath = os.path.abspath('.') + u'\\专有名词\\'.encode('gb2312')
    if not os.path.isdir(dicPath):
        os.mkdir(dicPath)
    dicPath = dicPath + ti[1] + '\\'
    if not os.path.isdir(dicPath):
        os.mkdir(dicPath)
    wi = GetSeparattion(ti[0])
    tn = ti[0][wi[0]:wi[1]]
    pattern = re.compile('\S+')
    wordList = []
    try:
        f = open(dicPath + tn + '.txt', 'r')
        wordList = pattern.findall(f.read())
        f.close()
    except:
        None
    wordList.append(ti[0])
    f = open(dicPath + tn + '.txt', 'w')
    f.write('\n'.join(sorted(set(wordList))))
    f.close()
    print(w)


def BatchProcessByEdiDis():
    sourcePath = os.path.abspath('.') + '\\special2\\'
    if not os.path.isdir(sourcePath):
        return
    savePath = os.path.abspath('.') + '\\Done\\'
    if not os.path.isdir(savePath):
        os.mkdir(savePath)
    taggedPath = os.path.abspath('.') + '\\taggedFile\\'
    if not os.path.isdir(taggedPath):
        os.mkdir(taggedPath)
    pattern = re.compile(r'\S+(?=/)')
    for f in os.listdir(sourcePath):
        fi = open(sourcePath + f, 'r')
        wordList = pattern.findall(fi.read())
        fi.close()
        for i in range(len(wordList)):
            wordList[i] = wordList[i] + MiniEdiTag(wordList[i])
            s = wordList[i].split(':')[0]
            RefreshN(s)
            CreatDic(s)
            CreatONDic(s)
            MorphemeRoleDic(s)
        fi = open(taggedPath + f, 'w')
        fi.write('\n'.join(wordList))
        fi.close()
        try:
            shutil.copy(sourcePath + f, savePath + f)
            #os.remove(sourcePath+f)
        except:
            None


def BatchProcessByRec():
    sourcePath = os.path.abspath('.') + '\\special2\\'
    if not os.path.isdir(sourcePath):
        return
    savePath = os.path.abspath('.') + '\\Done2\\'
    if not os.path.isdir(savePath):
        os.mkdir(savePath)
    taggedPath = os.path.abspath('.') + '\\taggedFile2\\'
    if not os.path.isdir(taggedPath):
        os.mkdir(taggedPath)
    pattern = re.compile(r'\S+(?=/)')
    for f in os.listdir(sourcePath):
        fi = open(sourcePath + f, 'r')
        wordList = pattern.findall(fi.read())
        fi.close()
        for i in range(len(wordList)):
            wordList[i] = ONRecognition(wordList[i] + '/')[0]
            s = wordList[i]
            RefreshN(s)
            CreatDic(s)
            CreatONDic(s)
            MorphemeRoleDic(s)
        fi = open(taggedPath + f, 'w')
        fi.write('\n'.join(wordList))
        fi.close()
        try:
            shutil.copy(sourcePath + f, savePath + f)
            #os.remove(sourcePath+f)
        except:
            None


if __name__ == '__main__':
    time1 = time.time()
    #findGongjiao()
    #BatchProcess()
    '''app=wx.PySimpleApp()
    fram=handleScelFrame()
    fram.Show()
    app.MainLoop()'''
    '''app=wx.PySimpleApp()
    fram=handleScelFrame2()
    fram.Show()
    app.MainLoop()
    f=open(os.path.abspath('.')+'\\handleIndex.txt','w')
    f.write(str(Index))
    print Index
    f.close()'''
    #removeSameDir()
    #BatchProcess()
    #GetSRTZ2()
    '''app=wx.PySimpleApp()
    fram=handleScelFrame3()
    fram.Show()
    app.MainLoop()
    BatchTest('','')'''
    BatchReProcess()
    #ReTagging()
    #MiniEdiDis(u'图示讲解'.encode('gb2312'),u'图示讲'.encode('gb2312'))
    #print MiniEdiTag(u'万家福超市'.encode('gb2312'))
    #ReTagging2()
    BatchProcessByEdiDis()
    #BatchProcessByRec()
    BatchReProcess()
    print(time.time() - time1)
