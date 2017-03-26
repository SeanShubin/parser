package com.seanshubin.parser.domain

trait RuleLookup[A] {
  def lookupRuleByName(name: String): Rule[A]
}
