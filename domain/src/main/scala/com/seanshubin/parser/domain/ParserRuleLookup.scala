package com.seanshubin.parser.domain

import com.seanshubin.parser.domain.CalculatorToken.CalculatorNumber

class ParserRuleLookup extends RuleLookup[CalculatorToken] {
  private val rules: Seq[Rule[CalculatorToken]] = Seq(
    SequenceRule(this, "expression", "num", "remain"),
    ZeroOrMoreRule(this, "remain", "op-expr"),
    SequenceRule(this, "op-expr", "plus", "num"),
    ValueTypeRule(this, "num", classOf[CalculatorNumber]),
    ValueRule(this, "plus", CalculatorToken.Plus)
  )
  private val rulesMap: Map[String, Rule[CalculatorToken]] = rules.map(rule => (rule.thisRuleName, rule)).toMap

  override def lookupRuleByName(name: String): Rule[CalculatorToken] = rulesMap(name)
}
