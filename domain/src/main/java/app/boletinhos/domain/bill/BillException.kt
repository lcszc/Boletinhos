package app.boletinhos.domain.bill

data class InvalidMinimumBillValueException(
    val minimumValue: Long = Bill.MINIMUM_VALUE
) : IllegalArgumentException("Bill's value can't be lower than $minimumValue.")

data class InvalidMaximumBillValueException(
    val maximumValue: Long = Bill.MAXIMUM_VALUE
) : IllegalArgumentException("Bill's value can't be higher than $maximumValue.")

data class BillNameTooShortException(
    val minimumCount: Int = Bill.MINIMUM_NAME_COUNT
) : IllegalArgumentException("Bill's name doesn't match our standards. Minimum count is $minimumCount.")

data class BillNameTooLongException(
    val maximumCount: Int = Bill.MAXIMUM_NAME_COUNT
): IllegalArgumentException("Bill's name doesn't match our standards. Maximum count is $maximumCount.")

data class BillDescriptionTooShortException(
    val minimumCount: Int = Bill.MINIMUM_DESCRIPTION_COUNT
) : IllegalArgumentException("Bill's description doesn't match our standards. Minimum count is $minimumCount.")

data class BillDescriptionTooLongException(
    val maximumCount: Int = Bill.MAXIMUM_DESCRIPTION_COUNT
): IllegalArgumentException("Bill's description doesn't match our standards. Maximum count is $maximumCount.")