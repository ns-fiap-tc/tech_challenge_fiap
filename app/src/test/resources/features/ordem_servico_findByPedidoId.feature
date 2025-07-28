Feature: Find OrdemServico by pedidoId

  Scenario: Find OrdemServico object by pedidoId.
    Given I create an OrdemServico and store to database.
      | nome         | pedidoId | pedidoItemId | produtoId | quantidade | status     | tempoPreparo |
      | Hamburger    | 1        | 1            | 1         | 3          | AGUARDANDO | 5            |
      | Cheeseburger | 1        | 2            | 1         | 1          | AGUARDANDO | 5            |
      | Cheesebacon  | 2        | 1            | 1         | 1          | AGUARDANDO | 5            |
    When I search by the pedidoId 1.
    Then I should find 2 object(s).