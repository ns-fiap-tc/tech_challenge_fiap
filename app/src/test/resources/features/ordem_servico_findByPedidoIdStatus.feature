Feature: Find OrdemServico by pedidoIdStatus

  Background:
    Given I create an OrdemServico and store to database.
      | nome         | pedidoId | pedidoItemId | produtoId | quantidade | status     | tempoPreparo |
      | Hamburger    | 4        | 1            | 1         | 3          | FINALIZADO | 5            |
      | Cheeseburger | 4        | 2            | 1         | 1          | AGUARDANDO | 6            |
      | Cheesebacon  | 5        | 1            | 1         | 1          | FINALIZADO | 7            |

  Scenario: Find OrdemServico objects by pedidoId and status not equals to FINALIZADO.
    When I search by pedidoId 4 and status "FINALIZADO".
    Then I should find 1 object(s).

  Scenario: Find OrdemServico objects by pedidoId and status not equals to FINALIZADO.
    When I search by pedidoId 5 and status "FINALIZADO".
    Then I should find 0 object(s).