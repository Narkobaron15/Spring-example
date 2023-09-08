export default interface ProductImageDTO {
    get id(): number;
    get priority(): number;

    get xs(): string;
    get sm(): string;
    get md(): string;
    get lg(): string;
    get xl(): string;
}
