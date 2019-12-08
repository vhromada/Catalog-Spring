package cz.vhromada.catalog.web.controller

import cz.vhromada.validation.result.Result
import cz.vhromada.validation.result.Status

/**
 * An abstract class represents controller for processing result.
 *
 * @author Vladimir Hromada
 */
abstract class AbstractResultController {

    /**
     * Process results.
     *
     * @param results results
     * @throws IllegalArgumentException if results aren't OK
     */
    protected fun processResults(vararg results: Result<*>) {
        val result = Result<Void>()
        for (resultItem in results) {
            result.addEvents(resultItem.events())
        }

        require(Status.OK == result.status) { "Operation result with errors. $result" }
    }

}
