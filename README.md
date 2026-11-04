# SK-KUNJU

Cleaned entity and relationship descriptions taken from a Cobo and crypto knowledge-graph extract.

The source payload named 31 entities and omitted 16 of them. This catalog keeps the 15 complete entity records and the relationship statements that were included. Descriptions merge repeated fragments into one paragraph per entity. Nothing was added for the omitted entities.

The data file is [`data/crypto-entities.json`](data/crypto-entities.json).

The Android app in [`android/`](android/) is a visual model of the supplied 9MX screens: welcome, sign up, login, and the home shell with Home, Fund, Spot, Team, and Asset. Sign-up and login stay on the device. The app does not create an account, send data, show balances, or take deposits. A debug APK is at [`dist/ninemx-debug.apk`](dist/ninemx-debug.apk). Rebuild with the Android SDK installed:

```bash
cd android
./gradlew assembleDebug
```

## Entities

| Name | Type | Description |
| --- | --- | --- |
| Blockchain Memo Chains | concept | XRP, EOS, XLM, IOST, BNB_BNB, ATOM, LUNA, and TON require a memo so a transfer is credited correctly. |
| BTC | artifact | UTXO chain in Cobo WaaS 2.0. Four confirmations is the usual threshold, coinbase maturity is 100, and the API uses `BTC` as the sample chain and token id. Staking is through Babylon. Bridging and WBTC conversion are supported. |
| Bitcoin (BTC) | data | Bitcoin as it appears in registration details for BTC addresses. |
| Total Assets Trend | data | Dashboard series of asset value over selectable timeframes and display currencies, including BTC and ETH. |
| Bridge Feature | artifact | Token Swap tool that moves assets such as USDC, USDT, BTC, and ETH across chains. Cross-chain swaps are not part of the swap feature. |
| Signing Requests | content | Signature requests sent to MPC wallet participants. |
| Digital Assets | data | Programmable holdings in the Cobo stack, covering more than 3,000 tokens across Portal, mobile, and WaaS 2.0, protected by policies and Cobo Guard. |
| KSM | other | MPC wallets must retain at least 0.000333333 KSM. |
| ICRC Assets | data | Internet Computer assets such as ckBTC, ckETH, and ckUSDC on a fixed fee model. |
| MPC Wallet | artifact | Threshold wallet whose key is split into shares. Cobo offers organization-controlled and user-controlled forms, with sweeping, staking, bridging, and Fee Station support. |
| Internet Computer | organization | Network that hosts ICRC assets and uses a fixed fee model. |
| V1.9.0 | event | Release on February 19, 2025 for MPC wallet features, transaction signing, and schema property updates. |
| Crypto | method | Go library used for request signing, and also a payout channel that sends cryptocurrency to a blockchain address. |
| Supported Blockchain Networks | concept | Same memo-required set as Blockchain Memo Chains: XRP, EOS, XLM, IOST, BNB_BNB, ATOM, LUNA, and TON. |
| Crypto Address | data | Destination address for a crypto payout or settlement. Create and delete endpoints for these addresses are deprecated. |

## Relationships

- **Cobo → MPC Wallet.** Cobo operates the MPC infrastructure, TSS nodes, key shares, automated signing, and optional cold isolation.
- **Cobo Portal → MPC Wallet.** Portal is where organization-controlled and user-controlled MPC wallets, keys, and vault security are created and managed.
- **Fee Station → MPC Wallet.** Eligible MPC transactions can pay network fees in USDT or other stablecoins.
- **API Key → MPC Wallet.** Vault and project ids on a key apply only to MPC wallets, and the key must be tied to a wallet to receive its events.
- **Development Environment → MPC Wallet.** Quick start creates and tests the wallet in a development environment.
- **BTC → MPC Wallets.** MPC wallets can sign messages on Bitcoin.
- **BTC → Total Assets Trend** and **ETH → Total Assets Trend.** The trend view can denominate values in BTC or ETH.
- **BTC → Transaction.** BTC is the sample asset for transfer amount formatting.
- **Bridge Feature → Digital Assets.** Bridge moves USDC, USDT, BTC, and ETH.
- **Blockchain Memo Chains → Cobo Portal.** Portal recommends a memo on those chains.
- **ICRC Assets → Internet Computer.** The network hosts those assets and sets their fixed fees.
- **Digital Assets → Tokenization.** Tokenization is how users control programmable assets.
- **Cobo OTC → USD.** OTC converts digital assets to USD.
- **Cobo Portal → Litecoin.** Portal accepts Litecoin deposits.
- **Binance → Coin-m Futures.** Binance offers coin-margined futures.
- **Actual Payout Amount → Crypto Payout.** The amount is a token quantity sent to the recipient address.
- **Crypto → Crypto Address.** A crypto payout pays a specific blockchain address. The source text stopped there.
