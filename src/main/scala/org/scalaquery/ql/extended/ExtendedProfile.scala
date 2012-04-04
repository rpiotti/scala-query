package org.scalaquery.ql.extended

import org.scalaquery.ql._
import org.scalaquery.ql.basic._
import org.scalaquery.ast.{Drop, Take, Node, NullaryNode}

trait ExtendedProfile extends BasicProfile {
  type ImplicitT <: ExtendedImplicitConversions[_ <: ExtendedProfile]
}

trait ExtendedImplicitConversions[DriverType <: ExtendedProfile] extends BasicImplicitConversions[DriverType] {
  implicit def extendedQueryToDeleteInvoker[T](q: Query[ExtendedTable[T], T]): BasicDeleteInvoker[T] = new BasicDeleteInvoker(q, scalaQueryDriver)
}

object ExtendedQueryOps { //TODO remove
  final case class TakeDrop(take: Option[Int], drop: Option[Int]) extends QueryModifier with NullaryNode
}

class ExtendedColumnOptions extends BasicColumnOptions {
  val AutoInc = ExtendedColumnOption.AutoInc
}

object ExtendedColumnOptions extends ExtendedColumnOptions

object ExtendedColumnOption {
  case object AutoInc extends ColumnOption[Nothing, ExtendedProfile]
}

abstract class AbstractExtendedTable[T](_schemaName: Option[String], _tableName: String) extends AbstractBasicTable[T](_schemaName, _tableName) {
  type ProfileType <: ExtendedProfile
  override val O: ExtendedColumnOptions = ExtendedColumnOptions
}

abstract class ExtendedTable[T](_schemaName: Option[String], _tableName: String) extends AbstractExtendedTable[T](_schemaName, _tableName) {
  def this(_tableName: String) = this(None, _tableName)
  type ProfileType = ExtendedProfile
}