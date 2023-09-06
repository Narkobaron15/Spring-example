export default class StringUtils {
    static ShortenString(str: string, maxLength: number): string {
        return str.length <= maxLength ? str : str.substring(0, maxLength - 3) + '...';
    }
    
    static PriceToString(price?: number): string {
        return price !== undefined
        ? (price <= 0 ? "Безкоштовно" : `$` + Number(price).toFixed(2))
        : ``;
    }
}