package co.com.bancolombia.model.shared.cqrs;

public record Query<P, C>(P payload, C context) {
}
