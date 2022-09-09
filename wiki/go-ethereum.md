# go-ethereum 随笔

## 概念

* [ABIs](https://docs.soliditylang.org/en/develop/abi-spec.html) - The Contract Application Binary Interface (ABI) is the standard way to interact with contracts in the Ethereum ecosystem, both from outside the blockchain and for contract-to-contract interaction.
* Ethereum Virtual Machine（EVM）
* 域名币（namecoin）- first-to-file
* 彩色币（Colored coins）- UTXO
* 元币（Metacoins）
* 比特币系统限制
  * 缺少图灵完备性 - 不支持循环语句
  * 价值盲（Value-blindness）- UTXO脚本不能为账户的取款额度提供精细的的控制
  * 缺少状态 - UTXO只能是已花费或者未花费状态
  * 区块链盲（Blockchain-blindness）- UTXO看不到区块链的数据
* [Recursive Length Prefix](https://ethereum.org/en/developers/docs/data-structures-and-encoding/rlp/) (RLP) serialization is used extensively in Ethereum's execution clients.
